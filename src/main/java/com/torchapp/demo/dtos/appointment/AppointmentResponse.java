package com.torchapp.demo.dtos.appointment;

import com.torchapp.demo.enums.AppointmentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@AllArgsConstructor
public class AppointmentResponse {
    private Long id;
    private LocalDate date;
    private LocalTime startTime;
    private AppointmentStatus status;
    private Long userId;
    private Long petId;
    private Long petShopId;
    private Long serviceId;
    private Long slotId;
}
