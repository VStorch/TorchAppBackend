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

    @NotNull
    private Long ownerId;
}
