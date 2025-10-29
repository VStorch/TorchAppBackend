package com.torchapp.demo.dtos.pet;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class PetResponse {
    private Long id;
    private String name;
    private String species;
    private String breed;
    private Double weight;
    private LocalDate birthDate;
    private Long userId;
}