package com.torchapp.demo.mappers;

import com.torchapp.demo.dtos.slot.AvailableSlotResponse;
import com.torchapp.demo.models.AvailableSlot;

public class AvailableSlotMapper {

    public static AvailableSlotResponse toResponse(AvailableSlot slot) {
        return new AvailableSlotResponse(
                slot.getId(),
                slot.getDate(),
                slot.getStartTime(),
                slot.getEndTime(),
                slot.isBooked(),
                slot.getPetShopService().getId()
        );
    }
}
