package com.torchapp.demo.dtos.petshop;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PetShopUpdateRequest {
    private String name;
    private String address;
    private String phone;
    private String email;
    private String cnpj;
}
