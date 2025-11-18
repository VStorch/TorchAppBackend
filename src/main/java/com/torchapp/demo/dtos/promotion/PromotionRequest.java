package com.torchapp.demo.dtos.promotion;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PromotionRequest {

    @NotBlank(message = "Nome da promoção é obrigatório")
    @Size(min = 3, max = 50, message = "Nome deve ter entre 3 e 50 caracteres")
    private String name;

    @Size(max = 200, message = "Descrição deve ter no máximo 200 caracteres")
    private String description;

    @NotNull(message = "Validade é obrigatória")
    private LocalDate validity;

    @Size(min = 3, max = 20, message = "Código do cupom deve ter entre 3 e 20 caracteres")
    @Pattern(regexp = "^[A-Z0-9]+$", message = "Código do cupom deve conter apenas letras maiúsculas e números")
    private String couponCode;

    @Min(value = 1, message = "Desconto deve ser no mínimo 1%")
    @Max(value = 100, message = "Desconto deve ser no máximo 100%")
    private Double discountPercent;

    // ========== NOVO CAMPO ==========
    private Long petShopId;  // ← ADICIONAR ESTE CAMPO
    // ================================
}