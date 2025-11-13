package com.torchapp.demo.dtos.Schedule;

import com.torchapp.demo.models.Schedule;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// DTO de resposta
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleResponseDTO {
    private Long id;
    private Long petShopId;
    private String dayOfWeek;
    private String dayDisplayName;
    private String openTime;
    private String closeTime;
    private Boolean isActive;

    public static ScheduleResponseDTO fromEntity(Schedule schedule) {
        return new ScheduleResponseDTO(
                schedule.getId(),
                schedule.getPetShopId(),
                schedule.getDayOfWeek().name(),
                schedule.getDayOfWeek().getDisplayName(),
                schedule.getOpenTime().toString(),
                schedule.getCloseTime().toString(),
                schedule.getIsActive()
        );
    }
}
