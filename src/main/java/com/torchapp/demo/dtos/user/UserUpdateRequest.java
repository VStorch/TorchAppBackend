package com.torchapp.demo.dtos.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateRequest {

    @NotBlank(message = "Nome é um campo obrigatório")
    private String name;

    @NotBlank(message = "Sobrenome é um campo obrigatório")
    private String surname;

    @NotBlank(message = "Email é um campo obrigatório")
    @Email(message = "Email inválido")
    private String email;
}
