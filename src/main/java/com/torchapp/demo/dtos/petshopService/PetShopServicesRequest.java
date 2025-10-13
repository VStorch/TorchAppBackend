package com.torchapp.demo.dtos.petshopService;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PetShopServicesRequest {

    @NotBlank
    private String name;

    @DecimalMin(value = "0.00", inclusive = false)
    @NotNull
    private BigDecimal price;

    @NotNull
    private Long petShopId;
}
