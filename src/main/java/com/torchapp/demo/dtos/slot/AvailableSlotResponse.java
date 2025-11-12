package com.torchapp.demo.dtos.slot;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@AllArgsConstructor
public class AvailableSlotResponse {
    private Long id;
    private LocalDate date;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime startTime;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime endTime;

    private boolean booked;
    private Long petShopServiceId;
}
