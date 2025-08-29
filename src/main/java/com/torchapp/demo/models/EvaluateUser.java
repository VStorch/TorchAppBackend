package com.torchapp.demo.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "evaluate_users")
public class EvaluateUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(0)
    @Max(5)
    @NotNull
    private Integer rating;

    @Size(max = 500)
    @NotBlank
    private String comment;

    @NotNull
    private LocalDate date = LocalDate.now();

}
