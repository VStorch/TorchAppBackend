package com.torchapp.demo.repositories;

import com.torchapp.demo.enums.AppointmentStatus;
import com.torchapp.demo.models.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    boolean existsByUserIdAndPetShopIdAndStatus(Long userId, Long petShopId, AppointmentStatus status);
}
