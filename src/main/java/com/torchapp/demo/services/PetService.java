package com.torchapp.demo.services;

import com.torchapp.demo.dtos.pet.PetRequest;
import com.torchapp.demo.exceptions.ResourceNotFoundException;
import com.torchapp.demo.models.Pet;
import com.torchapp.demo.models.User;
import com.torchapp.demo.repositories.PetRepository;
import com.torchapp.demo.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PetService {
    private final PetRepository petRepository;
    private final UserRepository userRepository;

    public PetService(PetRepository petRepository, UserRepository userRepository) {
        this.petRepository = petRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Pet registerPet(PetRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(ResourceNotFoundException::new);
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

     public List<Pet> getPetsByUserId(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("Usuário não encontrado.");
        }
        return petRepository.findByUserId(userId);
     }

     public Pet getPetById(Long id) {
        return petRepository.findById(id).orElseThrow((ResourceNotFoundException::new));
     }

     @Transactional
     public Pet updatePet (Long id, Pet newPetData) {
        return petRepository.findById(id).map(pet -> {
            pet.setName(newPetData.getName());
            pet.setSpecies(newPetData.getSpecies());
            pet.setBreed(newPetData.getBreed());
            pet.setWeight(newPetData.getWeight());
            pet.setBirthDate(newPetData.getBirthDate());

            return petRepository.save(pet);
        }).orElseThrow(ResourceNotFoundException::new);
     }

     @Transactional
     public void deletePet (Long id) {
        if (!petRepository.existsById(id)) {
            throw new ResourceNotFoundException();
        }
        petRepository.deleteById(id);
     }
}
