package com.torchapp.demo.dtos.petshopService;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PetShopServicesRequest {
    private Long id;
    private String name;
    private BigDecimal price;
    private Long petShopId;
}
