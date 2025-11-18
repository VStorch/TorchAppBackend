package com.torchapp.demo.dtos.appointment;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class AppointmentRequest {

    @NotNull(message = "Id do Usuário é um campo obrigatório")
    private Long userId;

    @NotNull(message = "Id do Pet é um campo obrigatório")
    private Long petId;

    @NotNull(message = "Id do PetShop é um campo obrigatório")
    private Long petShopId;

    @NotNull(message = "Id do Serviço é um campo obrigatório")
    private Long serviceId;

    @NotNull(message = "Id do Slot é um campo obrigatório")
    private Long slotId;

    // ========== CAMPOS DE CUPOM (OPCIONAIS) ==========
    private String couponCode;
    private BigDecimal discountPercent;
    private BigDecimal finalPrice;
    // =================================================
}