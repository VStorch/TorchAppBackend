package com.torchapp.demo.dtos.schedule;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

// DTO de resposta para operações em lote
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleBulkResponseDTO {
    private Boolean success;
    private String message;
    private List<ScheduleResponseDTO> schedules;
}
