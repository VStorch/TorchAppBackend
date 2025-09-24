package com.torchapp.demo.controllers;

import com.torchapp.demo.models.Pet;
import com.torchapp.demo.services.PetService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pets")
public class PetController {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

//    public ResponseEntity<?> registerPet(@Valid @RequestBody ) {
//    return petService.registerPet()
//    }

    @GetMapping
    public List<Pet> getAllPets() {
        return petService.getPets();
    }
}
