package com.torchapp.demo.dtos.petshop;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PetShopResponse {
    private Long id;
    private String name;
    private String cep;
    private String state;
    private String city;
    private String neighborhood;
    private String street;
    private String number;
    private String addressComplement;
    private String phone;
    private String email;
    private String cnpj;
}
