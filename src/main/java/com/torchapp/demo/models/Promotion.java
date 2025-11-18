package com.torchapp.demo.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "promotions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Promotion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome da promoção é obrigatório")
    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @NotNull(message = "Validade é obrigatória")
    @Column(nullable = false)
    private LocalDate validity;

    @Column(unique = true, length = 20, name = "coupon_code")
    private String couponCode;

    @Column(name = "discount_percent")
    private Double discountPercent;

    // ========== NOVO CAMPO ==========
    @Column(name = "pet_shop_id")
    private Long petShopId;  // ← ADICIONAR ESTE CAMPO
    // ================================

    @Column(name = "created_at", updatable = false)
    private LocalDate createdAt;

    @Column(name = "updated_at")
    private LocalDate updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDate.now();
        updatedAt = LocalDate.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDate.now();
    }

    public Double calculateDiscount(Double originalPrice) {
        if (discountPercent == null || discountPercent <= 0 || originalPrice == null) {
            return originalPrice;
        }
        return originalPrice * (1 - (discountPercent / 100));
    }

    public boolean isValid() {
        return !LocalDate.now().isAfter(validity);
    }
}