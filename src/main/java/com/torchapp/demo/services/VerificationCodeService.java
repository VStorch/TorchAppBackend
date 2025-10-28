package com.torchapp.demo.services;

import com.torchapp.demo.exceptions.EmailSendException;
import com.torchapp.demo.exceptions.MaxAttemptsExceededException;
import com.torchapp.demo.exceptions.ResourceNotFoundException;
import com.torchapp.demo.exceptions.VerificationCodeExpiredException;
import com.torchapp.demo.models.VerificationCode;
import com.torchapp.demo.repositories.VerificationCodeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Slf4j
@Service
public class VerificationCodeService {

    private static final int EXPIRATION_MINUTES = 10;
    private static final int MAX_ATTEMPTS = 5;

    @Autowired
    private VerificationCodeRepository verificationCodeRepository;

    @Autowired
    private EmailService emailService;

    private final Random random = new Random();

    @Transactional
    public void sendVerificationCode(String email) {
        try {
            String code = String.valueOf(random.nextInt(90_000) + 10_000);

            VerificationCode verification = verificationCodeRepository
                    .findByEmail(email)
                    .orElse(new VerificationCode());

            verification.setEmail(email);
            verification.setCode(code);
            verification.setExpirationTime(LocalDateTime.now().plusMinutes(EXPIRATION_MINUTES));
            verification.setVerified(false);
            verification.setAttempts(0);

            verificationCodeRepository.save(verification);
            emailService.sendVerificationCodeMail(email, code);

            log.info("Código de verificação gerado para: {}", email);
        } catch (EmailSendException e) {
            log.error("Erro ao enviar código de verificação para: {}", email, e);
            throw e;
        } catch (Exception e) {
            log.error("Erro inesperado ao gerar código para {}", email, e);
            throw new RuntimeException("Erro ao processar código de verificação", e);
        }
    }

    @Transactional
    public boolean verifyCode(String email, String code) {
        VerificationCode verification = verificationCodeRepository
                .findByEmailForUpdate(email)
                .orElseThrow(() -> new ResourceNotFoundException("Nunhum código gerado para este PetShop."));

        if (verification.isExpired()) {
            log.warn("Tentativa de usar código expirado para: {}", email);
            throw new VerificationCodeExpiredException("O código expirou. Solicite um novo.");
        }
        if (verification.getAttempts() >= MAX_ATTEMPTS) {
            log.warn("Limite de tentativas excedido para: {}", email);
            throw new MaxAttemptsExceededException(
                    "Limite de tentativas excedido. Solicite um novo código.",
                    MAX_ATTEMPTS
            );
        }

        if (verification.getCode().equals(code)) {
            verification.setVerified(true);
            verification.setAttempts(verification.getAttempts() + 1);
            verificationCodeRepository.save(verification);
            log.info("Código verificado com sucesso para: {}", email);
            return true;
        } else {
            verification.setAttempts(verification.getAttempts() + 1);
            verificationCodeRepository.save(verification);

            int remaining = MAX_ATTEMPTS - verification.getAttempts();
            log.warn("Código incorreto para {}. Tentativas restantes: {}", email, remaining);

            throw new RuntimeException("Código incorreto. Tentativas restantes: " + remaining + " para " + verification.getId());
        }
    }

    @Transactional
    @Scheduled(fixedRate = 3600000)
    public void deleteExpiredCodes() {
        try {
            long deleted = verificationCodeRepository.findAll().stream()
                    .filter(VerificationCode::isExpired)
                    .peek(verificationCodeRepository::delete)
                    .count();
            if (deleted > 0) {
                log.info("Códigos expirados deletados: {}", deleted);
            }
        } catch (Exception e) {
            log.error("Erro ao deletar códigos expirados", e);
        }
    }

    @Transactional(readOnly = true)
    public boolean isEmailVerified(String email) {
        return verificationCodeRepository.findByEmail(email)
                .map(VerificationCode::isVerified)
                .orElse(false);
    }
}
