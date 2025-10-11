package com.torchapp.demo.mappers;

import com.torchapp.demo.dtos.petshopService.PetShopServicesRequest;
import com.torchapp.demo.dtos.petshopService.PetShopServicesResponse;
import com.torchapp.demo.models.PetShop;
import com.torchapp.demo.models.PetShopServices;

public class PetShopServicesMapper {

    public static PetShopServices toEntity(PetShopServicesRequest request, PetShop petShop) {
        PetShopServices petShopServices = new PetShopServices();
        petShopServices.setName(request.getName());
        petShopServices.setPrice(request.getPrice());
        petShopServices.setPetShop(petShop);
        return petShopServices;
    }

    public static PetShopServicesResponse toResponse(PetShopServices petShopServices) {
        return new PetShopServicesResponse(
                petShopServices.getId(),
                petShopServices.getName(),
                petShopServices.getPrice().toString(),
                petShopServices.getPetShop().getId()
        );
    }

}
