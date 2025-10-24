package com.torchapp.demo.dtos.slot;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AvailableSlotBulkRequest extends AvailableSlotRequest{

    @NotNull(message = "Intervalo em minutos é obrigatório")
    private Integer intervalMinutes;
}
