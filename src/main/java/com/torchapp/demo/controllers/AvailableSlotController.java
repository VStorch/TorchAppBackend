package com.torchapp.demo.controllers;

import com.torchapp.demo.dtos.slot.AvailableSlotBulkRequest;
import com.torchapp.demo.dtos.slot.AvailableSlotRequest;
import com.torchapp.demo.dtos.slot.AvailableSlotResponse;
import com.torchapp.demo.services.AvailableSlotService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/slots")
public class AvailableSlotController {

    private final AvailableSlotService availableSlotService;

    public AvailableSlotController(AvailableSlotService availableSlotService) {
        this.availableSlotService = availableSlotService;
    }

    // Método para gerar apenas um slot
    @PostMapping
    public ResponseEntity<AvailableSlotResponse> createSlot(@Valid @RequestBody AvailableSlotRequest request) {
        return ResponseEntity.status(201).body(availableSlotService.createSlot(request));
    }

    // Método para gerar vários slots de modo rápido
    @PostMapping("/bulk")
    public ResponseEntity<List<AvailableSlotResponse>> createSlotBulk(@Valid @RequestBody AvailableSlotBulkRequest request) {
        return ResponseEntity.status(201).body(availableSlotService.createSlots(request));
    }

    @GetMapping
    public ResponseEntity<List<AvailableSlotResponse>> getAvailableSlots(
            @RequestParam Long serviceId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate targetDate
    ) {
        return ResponseEntity.ok(availableSlotService.getAvailableSlots(serviceId, targetDate));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteSlot(@PathVariable Long id) {
        availableSlotService.deleteSlot(id);
        return ResponseEntity.noContent().build();
    }
}
