package com.torchapp.demo.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@Table(name = "available_slots")
public class AvailableSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private boolean booked = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "petshop_service_id", nullable = false)
    private PetShopServices petShopService;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "petshop_id", nullable = false)
    private PetShop petShop;

}