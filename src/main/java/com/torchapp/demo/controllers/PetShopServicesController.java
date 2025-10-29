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
    public List<PetShopServicesResponse> getAllServices() {
        return petShopServicesService.getPetShopServices()
                .stream()
                .map(PetShopServicesMapper::toResponse)
                .toList();
    }

    @GetMapping("/petshops/{petShopId}")
    public ResponseEntity<List<PetShopServicesResponse>> getServicesByPetShopId(@PathVariable Long petShopId) {
        List<PetShopServicesResponse> services = petShopServicesService.getPetShopServicesByPetShopId(petShopId)
                .stream()
                .map(PetShopServicesMapper::toResponse)
                .toList();
        return ResponseEntity.ok(services);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetShopServicesResponse> getServiceById(@PathVariable Long id) {
        PetShopServices service = petShopServicesService.getPetShopServiceById(id);
        return ResponseEntity.ok(PetShopServicesMapper.toResponse(service));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PetShopServicesResponse> updateService(@PathVariable Long id, @RequestBody PetShopServices service) {
        PetShopServices updateService = petShopServicesService.updateService(id, service);
        return ResponseEntity.ok(PetShopServicesMapper.toResponse(updateService));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable Long id) {
        petShopServicesService.deleteService(id);
        return ResponseEntity.noContent().build();
    }
}
