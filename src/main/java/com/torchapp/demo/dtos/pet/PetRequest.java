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

    @NotBlank(message = "Nome é um campo obrigatório")
    private String name;

    @NotBlank(message = "Espécie é um campo obrigatório")
    private String species;

    @NotBlank(message = "Raça é um campo obrigatório")
    private String breed;

    @NotNull(message = "Peso é um campo obrigatório")
    @Positive(message = "Não existe peso negativo")
    private Double weight;

    @Past(message = "A data de nascimento deve estar no passado")
    private LocalDate birthDate;

    @NotNull(message = "Id do Usuário é um campo obrigatório")
    private Long userId;
}
