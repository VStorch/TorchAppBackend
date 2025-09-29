package com.torchapp.demo.dtos.petshop;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PetShopRegistrationRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String address;

    @Pattern(regexp = "^\\+?\\d{10,15}$", message = "Telefone inválido")
    @NotBlank
    private String phone;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String cnpj;

    @Size(min = 8)
    @NotBlank
    private String password;
}
