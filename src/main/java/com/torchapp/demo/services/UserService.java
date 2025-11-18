package com.torchapp.demo.services;

import com.torchapp.demo.dtos.user.OwnerUpdateRequest;
import com.torchapp.demo.dtos.user.UserResponse;
import com.torchapp.demo.dtos.user.UserUpdateRequest;
import com.torchapp.demo.enums.Role;
import com.torchapp.demo.exceptions.BadRequestException;
import com.torchapp.demo.exceptions.PasswordResetException;
import com.torchapp.demo.exceptions.ResourceNotFoundException;
import com.torchapp.demo.mappers.UserMapper;
import com.torchapp.demo.models.User;
import com.torchapp.demo.repositories.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Optional<User> registerUser(User user) {
        if (emailExists(user.getEmail())) {
            throw new IllegalArgumentException("Email já cadastrado.");
        }
        user.setRole(Role.CLIENT);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return Optional.of(userRepository.save(user));
    }

    @Transactional
    public Optional<User> registerPetShopOwner(User user, VerificationCodeService verificationCodeService) {
        if (emailExists(user.getEmail())) {
            throw new IllegalArgumentException("Email já cadastrado.");
        }
        if (!verificationCodeService.isEmailVerified(user.getEmail())) {
            throw new IllegalStateException("Email não verificado.");
        }
        user.setRole(Role.PETSHOP_OWNER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return Optional.of(userRepository.save(user));
    }

    public List<UserResponse> getUsers() {
        return userRepository.findAll().stream()
                .map(UserMapper::toResponse)
                .toList();
    }

    private User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    public UserResponse getUserByIdResponse(Long id) {
        User user = getUserById(id);
        return UserMapper.toResponse(user);
    }

    @Transactional
    public User updateUser(Long id, UserUpdateRequest userUpdateRequest) {
        return userRepository.findById(id).map(user -> {
            user.setName(userUpdateRequest.getName());
            user.setSurname(userUpdateRequest.getSurname());
            user.setEmail(userUpdateRequest.getEmail());

            return userRepository.save(user);
        }).orElseThrow(ResourceNotFoundException::new);
    }

    @Transactional
    public User updateOwner(Long id, OwnerUpdateRequest request) {
        return userRepository.findById(id).map(user -> {
            user.setName(request.getName());
            user.setSurname(request.getSurname());
            user.setPhone(request.getPhone());
            user.setEmail(request.getEmail());

            return userRepository.save(user);
        }).orElseThrow(ResourceNotFoundException::new);
    }

    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException();
        }
        userRepository.deleteById(id);
    }

    public Optional<User> login(String email, String rawPassword) {
        return userRepository.findByEmail(email)
                .filter(user -> passwordEncoder.matches(rawPassword, user.getPassword()));
    }


    public boolean emailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional
    public Optional<User> generatePasswordResetToken(String email) {
        return userRepository.findByEmail(email).map(user -> {
            String token = UUID.randomUUID().toString();
            user.setResetToken(token);
            userRepository.save(user);
            return user;
        });
    }

    @Transactional
    public void resetPassword(String email, String token, String newPassword) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BadRequestException("Email inválido."));

        if (!token.equals(user.getResetToken())) {
            throw new BadRequestException("Token inválido ou expirado.");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetToken(null);
        userRepository.save(user);
    }

}
