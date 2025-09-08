package com.torchapp.demo.dtos;

import lombok.Getter;

@Getter
public class LoginRequest {
    private String email;
    private String password;
}
