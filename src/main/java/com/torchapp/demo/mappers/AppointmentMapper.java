package com.torchapp.demo.mappers;

import com.torchapp.demo.dtos.appointment.AppointmentResponse;
import com.torchapp.demo.models.Appointment;

public class AppointmentMapper {

    public static AppointmentResponse toResponse(Appointment appointment) {
        return new AppointmentResponse(
                appointment.getId(),
                appointment.getDate(),
                appointment.getTime(),
                appointment.getStatus(),
                appointment.getUser().getId(),
                appointment.getPet().getId(),
                appointment.getPetShop().getId(),
                appointment.getService().getId(),
                null
        );
    }
}
