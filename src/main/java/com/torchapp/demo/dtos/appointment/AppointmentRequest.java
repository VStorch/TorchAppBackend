package com.torchapp.demo.dtos.appointment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

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

}
