package com.torchapp.demo.repositories;

import com.torchapp.demo.models.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}
