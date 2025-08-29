package com.torchapp.demo.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "evaluate_pet_shops")
public class EvaluatePetShop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(0)
    @Max(5)
    @NotNull
    private Integer rating;

    @Size(max = 1000)
    private String comment;

    @NotNull
    private LocalDate date = LocalDate.now();

}
