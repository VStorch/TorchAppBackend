package com.torchapp.demo.dtos.pet;

import com.torchapp.demo.models.Pet;

import java.time.LocalDate;

public record PetResponse(
    Long id,
    String name,
    String species,
    String breed,
    Double weight,
    LocalDate birthDate,
    Long userId
) {
    public static PetResponse fromEntity(Pet pet) {
        return new PetResponse(
                pet.getId(),
                pet.getName(),
                pet.getSpecies(),
                pet.getBreed(),
                pet.getWeight(),
                pet.getBirthDate(),
                pet.getUser().getId()
        );
    }
}