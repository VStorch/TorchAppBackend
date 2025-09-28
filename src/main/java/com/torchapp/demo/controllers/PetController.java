package com.torchapp.demo.controllers;

import com.torchapp.demo.dtos.pet.PetRequest;
import com.torchapp.demo.models.Pet;
import com.torchapp.demo.models.User;
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
    public ResponseEntity<Pet> registerPet(@Valid @RequestBody PetRequest request) {
        Pet savedPet = petService.registerPet(request);
        return ResponseEntity.status(201).body(savedPet);
    }

    @GetMapping
    public List<Pet> getAllPets() {
        return petService.getPets();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pet> getPetById(@PathVariable Long id) {
        try {
            Pet pet = petService.getPetById(id);
            return ResponseEntity.ok(pet);
        }
        catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pet> updatePet(@PathVariable Long id, @RequestBody Pet pet) {
        try {
            Pet updatePet = petService.updatePet(id, pet);
            return ResponseEntity.ok(updatePet);
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
