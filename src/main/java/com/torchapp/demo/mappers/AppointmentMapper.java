package com.torchapp.demo.mappers;

import com.torchapp.demo.dtos.appointment.AppointmentResponse;
import com.torchapp.demo.models.Appointment;

public class AppointmentMapper {

    public static AppointmentResponse toResponse(Appointment appointment) {
        AppointmentResponse response = new AppointmentResponse();
        response.setId(appointment.getId());
        response.setDate(appointment.getDate());
        response.setTime(appointment.getTime());
        response.setStatus(appointment.getStatus());

        // IDs
        response.setUserId(appointment.getUser().getId());
        response.setPetId(appointment.getPet().getId());
        response.setPetShopId(appointment.getPetShop().getId());
        response.setServiceId(appointment.getService().getId());
        response.setSlotId(appointment.getSlot() != null ? appointment.getSlot().getId() : null);

        // NOMES
        response.setServiceName(appointment.getService().getName());
        response.setPetShopName(appointment.getPetShop().getName());
        response.setPetName(appointment.getPet().getName());
        response.setUserName(appointment.getUser().getName()); // ADICIONAR

        // PREÇO DO SERVIÇO
        response.setServicePrice(appointment.getService().getPrice()); // ADICIONAR

        // HORÁRIOS DO SLOT
        if (appointment.getSlot() != null) {
            response.setSlotStartTime(appointment.getSlot().getStartTime()); // ADICIONAR
            response.setSlotEndTime(appointment.getSlot().getEndTime()); // ADICIONAR
        }

        return response;
    }
}