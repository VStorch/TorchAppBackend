package com.torchapp.demo.dtos.user.verificationCodeForPetShopOwner;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class checkVerificationCodeRequest {

    @NotBlank(message = "Email é um campo obrigatório")
    @Email(message = "Email inválido")
    private String email;

    @NotBlank(message = "Código é um campo obrigatório")
    @Pattern(regexp = "\\d{5}", message = "O código deve conter 5 números")
    private String code;
}
