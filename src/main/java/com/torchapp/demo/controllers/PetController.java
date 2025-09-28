package com.torchapp.demo.controllers;

import com.torchapp.demo.dtos.pet.PetRequest;
import com.torchapp.demo.dtos.pet.PetResponse;
import com.torchapp.demo.models.Pet;
import com.torchapp.demo.services.PetService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pets")
public class PetController {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @PostMapping
    public ResponseEntity<PetResponse> registerPet(@Valid @RequestBody PetRequest request) {
        Pet savedPet = petService.registerPet(request);
        return ResponseEntity.status(201).body(PetResponse.fromEntity(savedPet));
    }

    @GetMapping
    public List<PetResponse> getAllPets() {
        return petService.getPets()
                .stream()
                .map(PetResponse::fromEntity)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetResponse> getPetById(@PathVariable Long id) {
        try {
            Pet pet = petService.getPetById(id);
            return ResponseEntity.ok(PetResponse.fromEntity(pet));
        }
        catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<PetResponse> updatePet(@PathVariable Long id, @RequestBody Pet pet) {
        try {
            Pet updatePet = petService.updatePet(id, pet);
            return ResponseEntity.ok(PetResponse.fromEntity(updatePet));
        }
        catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePet(@PathVariable Long id) {
        try {
            petService.deletePet(id);
            return ResponseEntity.noContent().build();
        }
        catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
