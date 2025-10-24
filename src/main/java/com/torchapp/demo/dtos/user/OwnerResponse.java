package com.torchapp.demo.dtos.user;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class OwnerResponse extends UserResponse {
    private String phone;
}
