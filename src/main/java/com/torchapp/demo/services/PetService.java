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

     public Pet getPetById(Long id) {
        return petRepository.findById(id).orElseThrow(() -> new RuntimeException("Pet não encontrado"));
     }

     public Pet updatePet (Long id, Pet newPetData) {
        return petRepository.findById(id).map(pet -> {
            pet.setName(newPetData.getName());

            return petRepository.save(pet);
        }).orElseThrow(() -> new RuntimeException("Pet não encontrado"));
     }

     public void deletePet (Long id) {
        if (!petRepository.existsById(id)) {
            throw new RuntimeException("Pet não encontrado");
        }
        petRepository.deleteById(id);
     }
}
