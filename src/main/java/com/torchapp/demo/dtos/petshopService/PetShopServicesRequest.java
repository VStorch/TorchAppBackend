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

    @NotBlank(message = "Nome é um campo obrigatório")
    private String name;

    @NotNull(message = "Preço é um campo obrigatório")
    @DecimalMin(value = "0.00", inclusive = false, message = "O preço deve ser maior que zero")
    private BigDecimal price;

    @NotNull(message = "Id do PetShop é um campo obrigatorio")
    private Long petShopId;
}
