package com.torchapp.demo.dtos.schedule;


import com.torchapp.demo.enums.DayOfWeek;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

// DTO para criar/atualizar um único horário
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleDTO {
    private Long id;
    private Long petShopId;
    private DayOfWeek dayOfWeek;
    private String openTime;  // formato "HH:mm"
    private String closeTime; // formato "HH:mm"
    private Boolean isActive;

    public LocalTime getOpenTimeAsLocalTime() {
        return LocalTime.parse(openTime);
    }

    public LocalTime getCloseTimeAsLocalTime() {
        return LocalTime.parse(closeTime);
    }
}

