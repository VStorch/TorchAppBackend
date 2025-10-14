package com.torchapp.demo.dtos.petshop.verificationCode;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class checkVerificationCodeRequest {

    @Email
    @NotBlank
    private String email;

    @Pattern(regexp = "\\d{5}")
    @NotBlank
    private String code;
}
