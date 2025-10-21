package com.torchapp.demo.dtos.petshop;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PetShopUpdateRequest {

    private String name;

    private String cep;
    private String state;
    private String city;
    private String neighborhood;
    private String street;
    private String number;
    private String addressComplement;

    @Pattern(regexp = "^\\(\\d{2}\\) \\d \\d{4}-\\d{4}$", message = "O telefone deve seguir o padrão: (XX) X XXXX-XXXX")
    private String phone;

    @Email(message = "Email inválido")
    private String email;

    @NotBlank(message = "CNPJ é um campo obrigatório")
    private String cnpj;
}
