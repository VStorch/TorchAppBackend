package com.torchapp.demo.services;

import com.torchapp.demo.exceptions.ResourceNotFoundException;
import com.torchapp.demo.models.AvailableSlot;
import com.torchapp.demo.models.PetShop;
import com.torchapp.demo.repositories.AvailableSlotRepository;
import com.torchapp.demo.repositories.PetShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AvailableSlotService {

    private final AvailableSlotRepository availableSlotRepository;
    private final PetShopRepository petShopRepository;

    public AvailableSlotService(AvailableSlotRepository availableSlotRepository, PetShopRepository petShopRepository) {
        this.availableSlotRepository = availableSlotRepository;
        this.petShopRepository = petShopRepository;
    }

    @Transactional
    public List<AvailableSlot> createSlots(Long petShopId, LocalDate date, LocalTime start, LocalTime end, int intervalMinutes) {
        PetShop petShop = petShopRepository.findById(petShopId)
                .orElseThrow(() -> new ResourceNotFoundException("PetShop não encontrado"));
        List<AvailableSlot> slots = new ArrayList<>();

        for (LocalTime time = start; time.isBefore(end); time = time.plusMinutes(intervalMinutes)) {
            AvailableSlot slot = new AvailableSlot();
            slot.setDate(date);
            slot.setStartTime(time);
            slot.setEndTime(time.plusMinutes(intervalMinutes));
            slot.setPetShop(petShop);
            slot.setBooked(false);
            slots.add(slot);
        }
        return availableSlotRepository.saveAll(slots);
    }

    public List<AvailableSlot> getAvailableSlots(Long petShopId, LocalDate time) {
        return availableSlotRepository.findByPetShopIdAndDateAndBookedFalse(petShopId, time);
    }
}
