package com.torchapp.demo.mappers;

import com.torchapp.demo.dtos.appointment.AppointmentResponse;
import com.torchapp.demo.models.Appointment;

public class AppointmentMapper {

    public static AppointmentResponse toResponse(Appointment appointment) {
        AppointmentResponse response = new AppointmentResponse();
        response.setId(appointment.getId());
        response.setDate(appointment.getDate());
        response.setTime(appointment.getTime());
        response.setStatus(appointment.getStatus()); // Changed this line

        // IDs
        response.setUserId(appointment.getUser().getId());
        response.setPetId(appointment.getPet().getId());
        response.setPetShopId(appointment.getPetShop().getId());
        response.setServiceId(appointment.getService().getId());
        response.setSlotId(appointment.getSlot() != null ? appointment.getSlot().getId() : null);

        // ADICIONAR OS NOMES - ISSO ESTAVA FALTANDO!
        response.setServiceName(appointment.getService().getName());
        response.setPetShopName(appointment.getPetShop().getName());
        response.setPetName(appointment.getPet().getName());

        return response;
    }
}