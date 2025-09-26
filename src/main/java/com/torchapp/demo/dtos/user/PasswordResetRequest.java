package com.torchapp.demo.dtos.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordResetRequest {
    @NotBlank
    @Email
    private String email;
}
