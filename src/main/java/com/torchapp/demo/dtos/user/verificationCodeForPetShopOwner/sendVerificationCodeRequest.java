package com.torchapp.demo.dtos.user.verificationCodeForPetShopOwner;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class sendVerificationCodeRequest {
    @Email(message = "Email inválido")
    @NotBlank(message = "Email é um campo obrigatório")
    private String email;
}
