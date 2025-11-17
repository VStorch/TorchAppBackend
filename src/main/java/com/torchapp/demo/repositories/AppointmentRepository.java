package com.torchapp.demo.repositories;

import com.torchapp.demo.enums.AppointmentStatus;
import com.torchapp.demo.models.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    boolean existsByUserIdAndPetShopIdAndStatus(Long userId, Long petShopId, AppointmentStatus status);

    // Verificar se slot está ocupado em uma data específica (ignorando cancelados)
    Optional<Appointment> findBySlotIdAndStatusNot(Long slotId, AppointmentStatus status);

    // Ou verificar apenas status PENDING
    Optional<Appointment> findBySlotIdAndStatus(Long slotId, AppointmentStatus status);
}