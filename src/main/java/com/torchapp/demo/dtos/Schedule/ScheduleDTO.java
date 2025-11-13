package com.torchapp.demo.dtos.Schedule;


import com.torchapp.demo.models.Schedule;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;

// DTO para criar/atualizar um único horário
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleDTO {
    private Long id;
    private Long petShopId;
    private Schedule.DayOfWeek dayOfWeek;
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

