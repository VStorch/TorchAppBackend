package com.torchapp.demo.dtos.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordResetConfirmRequest {
    @NotBlank(message = "Toke é um campo obrigatório")
    private String token;

    @NotBlank(message = "Senha é um campo obrigatório")
    @Size(min = 8, message = "A senha deve ter no mínimo 8 caracteres")
    private String newPassword;
}
