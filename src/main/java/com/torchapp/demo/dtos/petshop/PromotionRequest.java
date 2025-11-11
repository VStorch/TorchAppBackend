package com.torchapp.demo.dtos.petshop;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

// Request DTO
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PromotionRequest {

    @NotBlank(message = "Nome da promoção é obrigatório")
    private String name;

    private String description;

    @NotNull(message = "Validade é obrigatória")
    private LocalDate validity;
}

// Response DTO
@Data
@NoArgsConstructor
@AllArgsConstructor
class PromotionResponse {
    private Long id;
    private String name;
    private String description;
    private LocalDate validity;
    private LocalDate createdAt;
    private LocalDate updatedAt;
}