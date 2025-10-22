package com.torchapp.demo.controllers;

import com.torchapp.demo.dtos.appointment.AppointmentRequest;
import com.torchapp.demo.dtos.appointment.AppointmentResponse;
import com.torchapp.demo.mappers.AppointmentMapper;
import com.torchapp.demo.models.Appointment;
import com.torchapp.demo.services.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping
    public ResponseEntity<AppointmentResponse> createAppointment(@Valid @RequestBody AppointmentRequest request) {
        return ResponseEntity.status(201).body(appointmentService.createAppointment(request));
    }


}
