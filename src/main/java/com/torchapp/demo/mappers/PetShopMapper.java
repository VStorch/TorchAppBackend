package com.torchapp.demo.mappers;

import com.torchapp.demo.dtos.petshop.PetShopRegistrationRequest;
import com.torchapp.demo.dtos.petshop.PetShopResponse;
import com.torchapp.demo.models.PetShop;

public class PetShopMapper {

    public static PetShop toEntity(PetShopRegistrationRequest request) {
        PetShop petShop = new PetShop();
        petShop.setName(request.getName());
        petShop.setAddress(request.getAddress());
        petShop.setPhone(request.getPhone());
        petShop.setEmail(request.getEmail());
        petShop.setCnpj(request.getCnpj());
        petShop.setPassword(request.getPassword());
        return petShop;
    }

    public static PetShopResponse toResponse(PetShop petShop) {
        return new PetShopResponse(
                petShop.getId(),
                petShop.getName(),
                petShop.getAddress(),
                petShop.getPhone(),
                petShop.getEmail(),
                petShop.getCnpj()
        );
    }
}
