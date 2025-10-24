package com.torchapp.demo.dtos.user;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class UserResponse {
    private Long id;
    private String name;
    private String surname;
    private String email;
}
