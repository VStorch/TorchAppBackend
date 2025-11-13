package com.torchapp.demo.dtos.Schedule;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

// DTO para receber todos os horários de uma vez (como vem do Flutter)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleBulkDTO {
    private Long petShopId;
    private List<DaySchedule> schedules;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DaySchedule {
        private String day; // "Seg", "Ter", "Qua", etc.
        private String openTime;  // formato "HH:mm AM/PM" ou "HH:mm"
        private String closeTime; // formato "HH:mm AM/PM" ou "HH:mm"
    }
}
