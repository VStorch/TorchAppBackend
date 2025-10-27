package com.torchapp.demo.dtos.evaluate;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class EvaluatePetShopResponse {
    private Long id;
    private Integer rating;
    private String comment;
    private LocalDate date;
    private String petShopName;
    private Long petShopId;
}
