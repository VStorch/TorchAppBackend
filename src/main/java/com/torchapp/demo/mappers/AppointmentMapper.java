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

        response.setUserId(appointment.getUser().getId());
        response.setPetId(appointment.getPet().getId());
        response.setPetShopId(appointment.getPetShop().getId());
        response.setServiceId(appointment.getService().getId());
        response.setSlotId(appointment.getSlot() != null ? appointment.getSlot().getId() : null);

        response.setServiceName(appointment.getService().getName());
        response.setPetShopName(appointment.getPetShop().getName());
        response.setPetName(appointment.getPet().getName());
        response.setUserName(appointment.getUser().getName());

        response.setServicePrice(appointment.getService().getPrice());

        if (appointment.getSlot() != null) {
            response.setSlotStartTime(appointment.getSlot().getStartTime());
            response.setSlotEndTime(appointment.getSlot().getEndTime());
        }

        response.setCouponCode(appointment.getCouponCode());
        response.setDiscountPercent(appointment.getDiscountPercent());
        response.setFinalPrice(appointment.getFinalPrice());

        return response;
    }
}