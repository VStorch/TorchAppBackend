package com.torchapp.demo.dtos.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateRequest {
    private String name;
    private String surname;
    private String email;
}
