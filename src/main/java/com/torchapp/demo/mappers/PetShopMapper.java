package com.torchapp.demo.mappers;

import com.torchapp.demo.dtos.petshop.PetShopRegistrationRequest;
import com.torchapp.demo.dtos.petshop.PetShopResponse;
import com.torchapp.demo.models.PetShop;

public class PetShopMapper {

    public static PetShop toEntity(PetShopRegistrationRequest request) {
        PetShop petShop = new PetShop();
        petShop.setCnpj(request.getCnpj());
        return petShop;
    }

    public static PetShopResponse toResponse(PetShop petShop) {
        return new PetShopResponse(
                petShop.getId(),
                petShop.getName(),
                petShop.getCep(),
                petShop.getState(),
                petShop.getCity(),
                petShop.getNeighborhood(),
                petShop.getStreet(),
                petShop.getNumber(),
                petShop.getAddressComplement(),
                petShop.getPhone(),
                petShop.getEmail(),
                petShop.getCnpj()
        );
    }
}
