package com.torchapp.demo.dtos.petshop.verificationCode;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class sendVerificationCodeRequest {
    @Email
    @NotBlank
    private String email;
}
