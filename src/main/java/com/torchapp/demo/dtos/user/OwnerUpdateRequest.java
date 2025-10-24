package com.torchapp.demo.dtos.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OwnerUpdateRequest {

    @NotBlank(message = "Nome é um campo obrigatório")
    private String name;

    @NotBlank(message = "Sobrenome é um campo obrigatório")
    private String surname;

    @NotBlank(message = "Telefone é um campo obrigatório")
    @Pattern(regexp = "^\\(\\d{2}\\) \\d \\d{4}-\\d{4}$", message = "O telefone deve seguir o padrão: (XX) X XXXX-XXXX")
    private String phone;

    @NotBlank(message = "Email é um campo obrigatório")
    @Email(message = "Email inválido")
    private String email;
}
