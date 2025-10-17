package com.torchapp.demo.services;

import com.torchapp.demo.exceptions.ResourceNotFoundException;
import com.torchapp.demo.models.PetShop;
import com.torchapp.demo.models.VerificationCode;
import com.torchapp.demo.repositories.VerificationCodeRepository;
import jakarta.transaction.Transactional;
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

        VerificationCode verification = verificationCodeRepository.findByEmail(email).orElse(new VerificationCode());

        verification.setEmail(email);
        verification.setCode(code);
        verification.setExpiresAt(LocalDateTime.now().plusMinutes(EXPIRATION_MINUTES));
        verification.setAttempts(0);
        verification.setVerified(false);

        verificationCodeRepository.save(verification);
        emailService.sendVerificationCodeMail(email, code);
    }

    @Transactional
    public boolean verifyCode(String email, String code) {
        VerificationCode verification = verificationCodeRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Nunhum código gerado para este PetShop."));
        if (verification.isExpired()) {
            throw new RuntimeException("O código expirou. Solicite um novo.");
        }
        if (verification.getAttempts() >= MAX_ATTEMPTS) {
            throw new RuntimeException("Limite de tentativas excedido.");
        }

        verification.setAttempts(verification.getAttempts() + 1);

        if (verification.getCode().equals(code)) {
            verification.setVerified(true);
            verificationCodeRepository.save(verification);
            return true;
        } else {
            verificationCodeRepository.save(verification);
            throw new RuntimeException("Código incorreto");
        }
    }

    @Scheduled(fixedRate = 3600000)
    public void deleteExpiredCodes() {
        verificationCodeRepository.findAll().stream()
                .filter(VerificationCode::isExpired)
                .forEach(verificationCodeRepository::delete);
    }
}
