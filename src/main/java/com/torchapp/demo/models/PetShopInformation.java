package com.torchapp.demo.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pet_shop_information")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PetShopInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "logo_url", length = 500)
    private String logoUrl;

    @ElementCollection
    @CollectionTable(name = "pet_shop_information_services", joinColumns = @JoinColumn(name = "pet_shop_information_id"))
    @Column(name = "service", length = 100)
    private List<String> services = new ArrayList<>();

    @Column(name = "instagram", length = 200)
    private String instagram;

    @Column(name = "facebook", length = 200)
    private String facebook;

    @Column(name = "website", length = 200)
    private String website;

    @Column(name = "whatsapp", length = 20)
    private String whatsapp;

    @Column(name = "commercial_phone", length = 20)
    private String commercialPhone;

    @Column(name = "commercial_email", length = 100)
    private String commercialEmail;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}