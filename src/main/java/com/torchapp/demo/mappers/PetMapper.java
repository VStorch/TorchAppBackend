package com.torchapp.demo.mappers;

import com.torchapp.demo.dtos.pet.PetRequest;
import com.torchapp.demo.models.Pet;
import com.torchapp.demo.models.User;

public class PetMapper {

    public static Pet toEntity(PetRequest request, User user) {
        Pet pet = new Pet();
        pet.setName(request.getName());
        pet.setSpecies(request.getSpecies());
        pet.setBreed(request.getBreed());
        pet.setWeight(request.getWeight());
        pet.setBirthDate(request.getBirthDate());
        pet.setUser(user);
        return pet;
    }

}
