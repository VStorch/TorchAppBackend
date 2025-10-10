package com.torchapp.demo.dtos.petshopService;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PetShopServicesResponse {
    private Long id;
    private String name;
    private String price;
    private Long petShopId;
}
