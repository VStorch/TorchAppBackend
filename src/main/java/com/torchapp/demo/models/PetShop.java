package com.torchapp.demo.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "pet_shops")
public class PetShop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // Address;
    private String cep;
    private String state;
    private String city;
    private String neighborhood;
    private String street;
    private String number;
    private String addressComplement;

    private String phone;

    private String email;

    private String cnpj;

    @OneToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private User owner;

    @Column(nullable = false)
    private boolean approved = false;

    // Um PetShop pode ter muitos serviços
    @OneToMany(mappedBy = "petShop", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PetShopServices> services = new ArrayList<>();

    @OneToMany(mappedBy = "petShop", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EvaluateUser> evaluationsMade = new ArrayList<>();

    @OneToMany(mappedBy = "petShop", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EvaluatePetShop> evaluations = new ArrayList<>();

    @OneToMany(mappedBy = "petShop", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Appointment> appointments = new ArrayList<>();
}
