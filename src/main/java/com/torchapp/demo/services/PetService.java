package com.torchapp.demo.services;

import com.torchapp.demo.models.Pet;
import com.torchapp.demo.repositories.PetRepository;

import java.util.List;
import java.util.Optional;

public class PetService {
    private final PetRepository petRepository;

    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public Optional<Pet> registerPet(Pet pet) {
        return Optional.of(petRepository.save(pet));
    }

     public List<Pet> getPets() {
        return petRepository.findAll();
     }
}
