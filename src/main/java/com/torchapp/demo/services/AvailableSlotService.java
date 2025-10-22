package com.torchapp.demo.services;

import com.torchapp.demo.dtos.slot.AvailableSlotBulkRequest;
import com.torchapp.demo.dtos.slot.AvailableSlotRequest;
import com.torchapp.demo.dtos.slot.AvailableSlotResponse;
import com.torchapp.demo.exceptions.ResourceNotFoundException;
import com.torchapp.demo.mappers.AvailableSlotMapper;
import com.torchapp.demo.models.AvailableSlot;
import com.torchapp.demo.models.PetShop;
import com.torchapp.demo.repositories.AvailableSlotRepository;
import com.torchapp.demo.repositories.PetShopRepository;
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
    public AvailableSlotResponse createSlot(AvailableSlotRequest request) {
        PetShop petShop = petShopRepository.findById(request.getPetShopId())
                .orElseThrow(() -> new ResourceNotFoundException("PetShop não encontrado."));

        AvailableSlot slot = new AvailableSlot();
        slot.setPetShop(petShop);
        slot.setDate(request.getDate());
        slot.setStartTime(request.getStartTime());
        slot.setEndTime(request.getEndTime());
        slot.setBooked(false);

        AvailableSlot saved = availableSlotRepository.save(slot);
        return AvailableSlotMapper.toResponse(saved);
    }

    @Transactional
    public List<AvailableSlotResponse> createSlots(AvailableSlotBulkRequest request) {

        if (request.getStartTime().isAfter(request.getEndTime())) {
            throw new IllegalArgumentException("Horário inicial não pode ser depois do final.");
        }
        if (request.getIntervalMinutes() <= 0) {
            throw new IllegalArgumentException("O intervalo entre horários deve ser positivo.");
        }

        PetShop petShop = petShopRepository.findById(request.getPetShopId())
                .orElseThrow(() -> new ResourceNotFoundException("PetShop não encontrado"));
        List<AvailableSlot> slots = new ArrayList<>();

        for (LocalTime time = request.getStartTime(); time.isBefore(request.getEndTime()); time = time.plusMinutes(request.getIntervalMinutes())) {
            AvailableSlot slot = new AvailableSlot();
            slot.setPetShop(petShop);
            slot.setDate(request.getDate());
            slot.setStartTime(time);
            slot.setEndTime(time.plusMinutes(request.getIntervalMinutes()));
            slot.setBooked(false);
            slots.add(slot);
        }
        return availableSlotRepository.saveAll(slots)
                .stream().map(AvailableSlotMapper::toResponse).toList();
    }

    public List<AvailableSlotResponse> getAvailableSlots(Long petShopId, LocalDate targetDate) {
        return availableSlotRepository.findByPetShopIdAndDateAndBookedFalse(petShopId, targetDate)
                .stream()
                .map(AvailableSlotMapper::toResponse)
                .toList();
    }

    public void deleteSlot(Long id) {
        availableSlotRepository.deleteById(id);
    }
}
