package com.torchapp.demo.dtos.petshop;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PetShopRegistrationRequest {

    @NotBlank(message = "CNPJ é um campo obrigatório")
    private String cnpj;

    @NotBlank(message = "CEP é um campo obrigatório")
    private String cep;

    @NotBlank(message = "Estado é um campo obrigatório")
    private String state;

    @NotBlank(message = "Cidade é um campo obrigatório")
    private String city;

    @NotBlank(message = "Bairro é um campo obrigatório")
    private String neighborhood;

    @NotBlank(message = "Rua é um campo obrigatório")
    private String street;

    @NotBlank(message = "Número é um campo obrigatório")
    private String number;

    private String addressComplement;

    @NotNull
    private Long ownerId;
}
