package com.torchapp.demo.dtos.evaluate;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EvaluatePetShopRequest {

    @NotNull
    private Long userId;

    @NotNull
    private Long petShopId;

    @Min(1)
    @Max(5)
    @NotNull
    private Integer rating;

    @Size(max = 1000)
    private String comment;
}
