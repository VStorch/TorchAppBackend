package com.torchapp.demo.dtos.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegistrationRequest {

    @NotBlank(message = "Nome é um campo obrigatório")
    private String name;

    @NotBlank(message = "Sobrenome é um campo obrigatório")
    private String surname;

    @NotBlank(message = "Email é um campo obrigatório")
    @Email(message = "Email inválido")
    private String email;

    @NotBlank(message = "Senha é um campo obrigatório")
    @Size(min = 8, message = "A senha deve ter no mínimo 8 caracteres")
    private String password;

}
