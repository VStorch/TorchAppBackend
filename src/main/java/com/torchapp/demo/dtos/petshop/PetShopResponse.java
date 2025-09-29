package com.torchapp.demo.dtos.petshop;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PetShopResponse {
    private Long id;
    private String name;
    private String address;
    private String phone;
    private String email;
    private String cnpj;
}
