package com.torchapp.demo.dtos.promotion;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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