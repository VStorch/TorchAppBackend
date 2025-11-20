package com.torchapp.demo.mappers;

import com.torchapp.demo.dtos.user.OwnerRegistrationRequest;
import com.torchapp.demo.dtos.user.OwnerResponse;
import com.torchapp.demo.dtos.user.UserRegistrationRequest;
import com.torchapp.demo.dtos.user.UserResponse;
import com.torchapp.demo.models.User;

public class UserMapper {

    public static User toEntity(UserRegistrationRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setSurname(request.getSurname());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        return user;
    }

    public static User toEntityOwner(OwnerRegistrationRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setSurname(request.getSurname());
        user.setPhone(request.getPhone());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        return user;
    }

    public static UserResponse toResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .profileImage(user.getProfileImage())
                .build();
    }

    public static OwnerResponse toResponseOwner(User user) {
        return OwnerResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .phone(user.getPhone())
                .email(user.getEmail())
                .profileImage(user.getProfileImage())
                .build();
    }
}
