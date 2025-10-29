package com.torchapp.demo.dtos.slot;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class AvailableSlotRequest {

    @NotNull(message = "Id do serviço é um campo obrigatório.")
    private Long serviceId;

    @NotNull(message = "Data é um campo obrigatório.")
    private LocalDate date;

    @NotNull(message = "Horário de início é um campo obrigatório")
    private LocalTime startTime;

    @NotNull(message = "Horário de término é um campo obrigatório")
    private LocalTime endTime;

}
