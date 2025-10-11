package com.torchapp.demo.controllers;

import com.torchapp.demo.dtos.petshopService.PetShopServicesRequest;
import com.torchapp.demo.dtos.petshopService.PetShopServicesResponse;
import com.torchapp.demo.mappers.PetShopServicesMapper;
import com.torchapp.demo.models.PetShopServices;
import com.torchapp.demo.services.PetShopServicesService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public List<PetShopServices> getAllServices() {
        return petShopServicesService.getPetShopServices();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetShopServices> getServiceById(@PathVariable Long id) {
        try {
            PetShopServices service = petShopServicesService.getPetShopServiceById(id);
            return ResponseEntity.ok(service);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Implementar updateService

    public ResponseEntity<Void> deleteService(@PathVariable Long id) {
        try {
            petShopServicesService.deleteService(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
