package com.torchapp.demo.repositories;

import com.torchapp.demo.models.AvailableSlot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AvailableSlotRepository extends JpaRepository<AvailableSlot, Long> {
    List<AvailableSlot> findByPetShopIdAndDateAndBookedFalse(Long petShopId, LocalDate date);
}
