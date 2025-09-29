package com.torchapp.demo.controllers;

import com.torchapp.demo.dtos.petshop.PetShopRegistrationRequest;
import com.torchapp.demo.mappers.PetShopMapper;
import com.torchapp.demo.models.PetShop;
import com.torchapp.demo.services.EmailService;
import com.torchapp.demo.services.PetShopService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class PetShopController {

    private final PetShopService petShopService;
    private final EmailService emailService;

    public PetShopController(PetShopService petShopService, EmailService emailService) {
        this.petShopService = petShopService;
        this.emailService = emailService;
    }

    @PostMapping
    public ResponseEntity<?> registerPetShop(@Valid @RequestBody PetShopRegistrationRequest registrationRequest) throws MessagingException, UnsupportedOperationException {
        PetShop petShop = PetShopMapper.toEntity(registrationRequest);

        return petShopService.registerPetShop(petShop)
                .map(savedPetShop -> {
                    //emailService.sendMailWithInline(savedPetShop); Ajustar questão do envio de email
                    return ResponseEntity.status(201).body(PetShopMapper.toResponse(savedPetShop));
                })
                .orElse(ResponseEntity.status(401).body(null));
    }
}
