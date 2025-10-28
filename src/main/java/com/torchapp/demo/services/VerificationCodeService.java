package com.torchapp.demo.services;

import com.torchapp.demo.exceptions.ResourceNotFoundException;
import com.torchapp.demo.models.VerificationCode;
import com.torchapp.demo.repositories.VerificationCodeRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

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
        String code = String.valueOf(random.nextInt(90_000) + 10_000);

        VerificationCode verification = verificationCodeRepository.findByEmail(email).orElse(null);

        if (verification == null) {
            verification = new VerificationCode();
            verification.setEmail(email);
            verification.setAttempts(0);
        }

        verification.setCode(code);
        verification.setExpirationTime(LocalDateTime.now().plusMinutes(EXPIRATION_MINUTES));
        verification.setVerified(false);

        verificationCodeRepository.save(verification);
        emailService.sendVerificationCodeMail(email, code);
    }

    @Transactional
    public boolean verifyCode(String email, String code) {
        VerificationCode verification = verificationCodeRepository.findByEmailForUpdate(email)
                .orElseThrow(() -> new ResourceNotFoundException("Nunhum código gerado para este PetShop."));
        if (verification.isExpired()) {
            throw new RuntimeException("O código expirou. Solicite um novo.");
        }
        if (verification.getAttempts() + 1 > MAX_ATTEMPTS) {
            throw new RuntimeException("Limite de tentativas excedido.");
        }

        if (verification.getCode().equals(code)) {
            verification.setVerified(true);
            verificationCodeRepository.save(verification);
            return true;
        } else {
            verification.setAttempts(verification.getAttempts() + 1);
            verificationCodeRepository.save(verification);

            int remaining = MAX_ATTEMPTS - verification.getAttempts();
            throw new RuntimeException("Código incorreto. Tentativas restantes: " + remaining + " para " + verification.getId());
        }
    }

    @Transactional
    @Scheduled(fixedRate = 3600000)
    public void deleteExpiredCodes() {
        verificationCodeRepository.findAll().stream()
                .filter(VerificationCode::isExpired)
                .forEach(verificationCodeRepository::delete);
    }

    @Transactional(readOnly = true)
    public boolean isEmailVerified(String email) {
        return verificationCodeRepository.findByEmail(email)
                .map(VerificationCode::isVerified)
                .orElse(false);
    }
}
