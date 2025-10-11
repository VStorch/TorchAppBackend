package com.torchapp.demo.controllers;

import com.torchapp.demo.dtos.petshopService.PetShopServicesRequest;
import com.torchapp.demo.dtos.petshopService.PetShopServicesResponse;
import com.torchapp.demo.mappers.PetShopServicesMapper;
import com.torchapp.demo.models.PetShopServices;
import com.torchapp.demo.services.PetShopServicesService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/services")
public class PetShopServicesController {

    private final PetShopServicesService petShopServicesService;

    public PetShopServicesController(PetShopServicesService petShopServicesService) {
        this.petShopServicesService = petShopServicesService;
    }

    @PostMapping
    public ResponseEntity<PetShopServicesResponse> registerService(@Valid @RequestBody PetShopServicesRequest request) {
        PetShopServices savedService = petShopServicesService.registerService(request);
        return ResponseEntity.status(201).body(PetShopServicesMapper.toResponse(savedService));
    }

    public List<PetShopServices> getAllServices() {
        return petShopServicesService.getPetShopServices();
    }
}
