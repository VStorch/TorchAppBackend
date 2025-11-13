package com.torchapp.demo.controllers;

import com.torchapp.demo.dtos.Schedule.ScheduleBulkDTO;
import com.torchapp.demo.dtos.Schedule.ScheduleBulkResponseDTO;
import com.torchapp.demo.dtos.Schedule.ScheduleDTO;
import com.torchapp.demo.dtos.Schedule.ScheduleResponseDTO;
import com.torchapp.demo.models.Schedule;
import com.torchapp.demo.services.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ScheduleController {

    private final ScheduleService scheduleService;

    /**
     * Salvar/atualizar horários em lote (usado pelo Flutter)
     * POST /api/schedules/bulk
     */
    @PostMapping("/bulk")
    public ResponseEntity<ScheduleBulkResponseDTO> saveBulkSchedules(
            @Valid @RequestBody ScheduleBulkDTO bulkDTO) {
        ScheduleBulkResponseDTO response = scheduleService.saveBulkSchedules(bulkDTO);
        return ResponseEntity.ok(response);
    }

    /**
     * Criar um horário individual
     * POST /api/schedules
     */
    @PostMapping
    public ResponseEntity<ScheduleResponseDTO> createSchedule(
            @Valid @RequestBody ScheduleDTO scheduleDTO) {
        ScheduleResponseDTO created = scheduleService.createSchedule(scheduleDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Atualizar um horário existente
     * PUT /api/schedules/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<ScheduleResponseDTO> updateSchedule(
            @PathVariable Long id,
            @Valid @RequestBody ScheduleDTO scheduleDTO) {
        ScheduleResponseDTO updated = scheduleService.updateSchedule(id, scheduleDTO);
        return ResponseEntity.ok(updated);
    }

    /**
     * Buscar todos os horários de um Pet Shop
     * GET /api/schedules/petshop/{petShopId}
     */
    @GetMapping("/petshop/{petShopId}")
    public ResponseEntity<List<ScheduleResponseDTO>> getSchedulesByPetShop(
            @PathVariable Long petShopId) {
        List<ScheduleResponseDTO> schedules = scheduleService.getSchedulesByPetShopId(petShopId);
        return ResponseEntity.ok(schedules);
    }

    /**
     * Buscar horário de um dia específico
     * GET /api/schedules/petshop/{petShopId}/day/{dayOfWeek}
     */
    @GetMapping("/petshop/{petShopId}/day/{dayOfWeek}")
    public ResponseEntity<ScheduleResponseDTO> getScheduleByDay(
            @PathVariable Long petShopId,
            @PathVariable Schedule.DayOfWeek dayOfWeek) {
        ScheduleResponseDTO schedule = scheduleService.getScheduleByDay(petShopId, dayOfWeek);
        return ResponseEntity.ok(schedule);
    }

    /**
     * Deletar um horário específico
     * DELETE /api/schedules/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id) {
        scheduleService.deleteSchedule(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Deletar todos os horários de um Pet Shop
     * DELETE /api/schedules/petshop/{petShopId}
     */
    @DeleteMapping("/petshop/{petShopId}")
    public ResponseEntity<Void> deleteAllSchedulesByPetShop(@PathVariable Long petShopId) {
        scheduleService.deleteAllSchedulesByPetShop(petShopId);
        return ResponseEntity.noContent().build();
    }
}