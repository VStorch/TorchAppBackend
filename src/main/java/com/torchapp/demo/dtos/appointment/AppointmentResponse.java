package com.torchapp.demo.dtos.appointment;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.torchapp.demo.enums.AppointmentStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
public class AppointmentResponse {
    private Long id;
    private LocalDate date;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime time;

    private AppointmentStatus status;

    // IDs
    private Long userId;
    private Long petId;
    private Long petShopId;
    private Long serviceId;
    private Long slotId;

    // NOMES
    private String serviceName;
    private String petShopName;
    private String petName;
}