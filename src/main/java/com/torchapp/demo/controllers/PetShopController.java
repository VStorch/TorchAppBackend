package com.torchapp.demo.controllers;

import com.torchapp.demo.dtos.petshop.PetShopRegistrationRequest;
import com.torchapp.demo.dtos.petshop.PetShopResponse;
import com.torchapp.demo.dtos.petshop.PetShopUpdateRequest;
import com.torchapp.demo.mappers.PetShopMapper;
import com.torchapp.demo.models.PetShop;
import com.torchapp.demo.services.EmailService;
import com.torchapp.demo.services.PetShopService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

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

    @GetMapping
    public List<PetShop> getAllPetShops() {
        return petShopService.getPetShops();
    }

    @GetMapping("/{id}")
    public PetShop getPatShopById(Long id) {
        return petShopService.getPetShopById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PetShopResponse> updatePetShop(Long id, PetShopUpdateRequest petShopUpdateRequest) {
        try {
            PetShop updatePetShop = petShopService.updatePetShop(id, petShopUpdateRequest);
            return ResponseEntity.ok(PetShopMapper.toResponse(updatePetShop));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
