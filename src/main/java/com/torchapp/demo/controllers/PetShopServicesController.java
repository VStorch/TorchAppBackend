package com.torchapp.demo.controllers;

import com.torchapp.demo.dtos.petshopService.PetShopServicesRequest;
import com.torchapp.demo.dtos.petshopService.PetShopServicesResponse;
import com.torchapp.demo.models.PetShopServices;
import com.torchapp.demo.services.PetShopServicesService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class PetShopServicesController {

    private final PetShopServicesService petShopServicesService;

    public PetShopServicesController(PetShopServicesService petShopServicesService) {
        this.petShopServicesService = petShopServicesService;
    }

    @PostMapping
    public ResponseEntity<PetShopServicesResponse> registerService(@Valid @RequestBody PetShopServicesRequest request) {
        PetShopServices savedService = petShopServicesService.registerService(request);
        return ResponseEntity.status(201).body(// Implementar um mapper);
    }
}
