package com.torchapp.demo.services;

import com.torchapp.demo.dtos.pet.PetRequest;
import com.torchapp.demo.models.Pet;
import com.torchapp.demo.models.User;
import com.torchapp.demo.repositories.PetRepository;
import com.torchapp.demo.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PetService {
    private final PetRepository petRepository;
    private final UserRepository userRepository;

    public PetService(PetRepository petRepository, UserRepository userRepository) {
        this.petRepository = petRepository;
        this.userRepository = userRepository;
    }

    public Pet registerPet(PetRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        Pet pet = new Pet();
        pet.setName(request.getName());
        pet.setSpecies(request.getSpecies());
        pet.setBreed(request.getBreed());
        pet.setWeight(request.getWeight());
        pet.setBirthDate(request.getBirthDate());
        pet.setUser(user);

        return petRepository.save(pet);
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
