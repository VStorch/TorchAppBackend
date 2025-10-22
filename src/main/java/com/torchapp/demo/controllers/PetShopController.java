package com.torchapp.demo.controllers;

import com.torchapp.demo.dtos.ErrorResponse;
import com.torchapp.demo.dtos.petshop.PetShopRegistrationRequest;
import com.torchapp.demo.dtos.petshop.PetShopResponse;
import com.torchapp.demo.dtos.petshop.PetShopUpdateRequest;
import com.torchapp.demo.dtos.LoginRequest;
import com.torchapp.demo.mappers.PetShopMapper;
import com.torchapp.demo.models.PetShop;
import com.torchapp.demo.services.EmailService;
import com.torchapp.demo.services.PetShopService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/petshops")
public class PetShopController {

    private final PetShopService petShopService;
    private final EmailService emailService;

    public PetShopController(PetShopService petShopService, EmailService emailService) {
        this.petShopService = petShopService;
        this.emailService = emailService;
    }

    @PostMapping
    public ResponseEntity<?> registerPetShop(@Valid @RequestBody PetShopRegistrationRequest registrationRequest) throws MessagingException, UnsupportedOperationException {

        return petShopService.registerPetShop(registrationRequest)
                .map(savedPetShop -> {
                    //emailService.sendMailWithInline(savedPetShop); Ajustar questão do envio de email
                    return ResponseEntity.status(201).body(PetShopMapper.toResponse(savedPetShop));
                })
                .orElse(ResponseEntity.status(401).body(null));
    }

    @GetMapping
    public ResponseEntity<List<PetShopResponse>> getAllPetShops() {
        return ResponseEntity.ok(petShopService.getPetShops());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetShopResponse> getPetShopById(@PathVariable Long id) {
        return ResponseEntity.ok(petShopService.getPetShopByIdResponse(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PetShopResponse> updatePetShop(@PathVariable Long id, @RequestBody PetShopUpdateRequest petShopUpdateRequest) {
        PetShop updatePetShop = petShopService.updatePetShop(id, petShopUpdateRequest);
        return ResponseEntity.ok(PetShopMapper.toResponse(updatePetShop));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePetShop(@PathVariable Long id) {
        petShopService.deletePetShop(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Void> emailExists(@PathVariable String email) {
        boolean exists = petShopService.emailExists(email);
        return exists
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }
}
