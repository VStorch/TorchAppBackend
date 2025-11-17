package com.torchapp.demo.models;

import com.torchapp.demo.enums.AppointmentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@Table(name = "appointments")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private LocalDate date;

    @NotNull
    private LocalTime time;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status = AppointmentStatus.PENDING;

    @ManyToOne(fetch = FetchType.EAGER) // ← MUDOU AQUI
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER) // ← MUDOU AQUI
    @JoinColumn(name = "pet_id", nullable = false)
    private Pet pet;

    @ManyToOne(fetch = FetchType.EAGER) // ← MUDOU AQUI
    @JoinColumn(name = "petshop_id", nullable = false)
    private PetShop petShop;

    @ManyToOne(fetch = FetchType.EAGER) // ← MUDOU AQUI
    @JoinColumn(name = "service_id", nullable = false)
    private PetShopServices service;

    @OneToOne(fetch = FetchType.EAGER) // ← MUDOU AQUI
    @JoinColumn(name = "slot_id", nullable = false)
    private AvailableSlot slot;
}