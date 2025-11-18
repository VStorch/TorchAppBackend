package com.torchapp.demo.models;

import com.torchapp.demo.enums.AppointmentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pet_id", nullable = false)
    private Pet pet;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "petshop_id", nullable = false)
    private PetShop petShop;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "service_id", nullable = false)
    private PetShopServices service;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "slot_id", nullable = false)
    private AvailableSlot slot;

    // ========== CAMPOS DE CUPOM DE DESCONTO ==========
    @Column(name = "coupon_code", length = 20)
    private String couponCode;

    @Column(name = "discount_percent")
    private BigDecimal discountPercent;

    @Column(name = "final_price")
    private BigDecimal finalPrice;
    // =================================================

    @PrePersist
    protected void onCreate() {
        // Se não tem desconto, o preço final é o preço original do serviço
        if (finalPrice == null && service != null) {
            finalPrice = service.getPrice();
        }
    }

    // Método auxiliar para verificar se tem desconto aplicado
    public boolean hasDiscount() {
        return couponCode != null && !couponCode.isEmpty()
                && discountPercent != null && discountPercent.compareTo(BigDecimal.ZERO) > 0;
    }

    // Método para calcular o valor do desconto
    public BigDecimal getDiscountAmount() {
        if (!hasDiscount() || service == null) {
            return BigDecimal.ZERO;
        }
        return service.getPrice().subtract(finalPrice);
    }
}