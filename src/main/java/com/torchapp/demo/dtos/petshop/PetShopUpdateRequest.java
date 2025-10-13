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

    @NotBlank
    private String name;

    @NotBlank
    private String address;

    @Pattern(regexp = "^\\(\\d{2}\\) \\d \\d{4}-\\d{4}$")
    @NotBlank
    private String phone;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String cnpj;
}
