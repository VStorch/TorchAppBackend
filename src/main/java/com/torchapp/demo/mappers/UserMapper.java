package com.torchapp.demo.mappers;

import com.torchapp.demo.dtos.user.RegistrationRequest;
import com.torchapp.demo.dtos.user.UserResponse;
import com.torchapp.demo.models.User;

public class UserMapper {

    public static User toEntity(RegistrationRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setSurname(request.getSurname());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        return user;
    }

    public static UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getSurname(),
                user.getEmail()
        );
    }
}
