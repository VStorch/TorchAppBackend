package com.torchapp.demo.dtos.pet;

import com.torchapp.demo.models.Pet;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PetRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String species;

    @NotBlank
    private String breed;

    @Positive
    @NotNull
    private Double weight;

    @Past
    private LocalDate birthDate;

    @NotNull
    private Long userId;
}
