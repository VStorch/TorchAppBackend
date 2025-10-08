package com.torchapp.demo.dtos.pet;

import com.torchapp.demo.models.Pet;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PetRequest {
    private Long id;
    private String name;
    private String species;
    private String breed;
    private Double weight;
    private LocalDate birthDate;
    private Long userId;
}
