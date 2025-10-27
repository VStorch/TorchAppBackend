package com.torchapp.demo.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "evaluate_pet_shops")
public class EvaluatePetShop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer rating;

    private String comment;

    @Column(nullable = false, updatable = false)
    private LocalDate date;

    // EvaluatePetShop pertence a um PetShop avaliado
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "petshop_id", nullable = false)
    private PetShop petShop;

    // EvaluatePetShop pertence a um User avaliador
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @PrePersist
    protected void onCreate() {
        this.date = LocalDate.now();
    }
}
