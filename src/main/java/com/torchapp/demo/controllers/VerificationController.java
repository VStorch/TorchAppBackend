package com.torchapp.demo.controllers;

import com.torchapp.demo.dtos.user.verificationCodeForPetShopOwner.checkVerificationCodeRequest;
import com.torchapp.demo.dtos.user.verificationCodeForPetShopOwner.sendVerificationCodeRequest;
import com.torchapp.demo.services.PetShopService;
import com.torchapp.demo.services.VerificationCodeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/verification")
public class VerificationController {

    private final VerificationCodeService verificationCodeService;

    public VerificationController(VerificationCodeService verificationCodeService) {
        this.verificationCodeService = verificationCodeService;
    }

    @PostMapping("/send")
    public ResponseEntity<?> sendVerificationCode(@RequestBody @Valid sendVerificationCodeRequest request) {
        verificationCodeService.sendVerificationCode(request.getEmail());
        return ResponseEntity.ok(Map.of("message", "Código de verificação enviado com sucesso."));
    }

    @PostMapping("/check")
    public ResponseEntity<?> verifyCode(@RequestBody @Valid checkVerificationCodeRequest request) {
        boolean success = verificationCodeService.verifyCode(request.getEmail(), request.getCode());

        if (success) {
            return ResponseEntity.ok(Map.of("message", "Email verificado com sucesso"));
        } else {
            return ResponseEntity.badRequest().body(Map.of("message", "Código incorreto ou expirado"));
        }
    }

}
