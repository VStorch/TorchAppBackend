package com.torchapp.demo.services;

import com.torchapp.demo.dtos.user.UserUpdateRequest;
import com.torchapp.demo.models.User;
import com.torchapp.demo.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<User> registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return Optional.of(userRepository.save(user));
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    public User updateUser(Long id, UserUpdateRequest userUpdateRequest) {
        return userRepository.findById(id).map(user -> {
            user.setName(userUpdateRequest.getName());
            user.setSurname(userUpdateRequest.getSurname());
            user.setEmail(userUpdateRequest.getEmail());
            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("Usuário não encontrado");
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

    public Optional<User> generatePasswordResetToken(String email) {
        return userRepository.findByEmail(email).map(user -> {
            String token = UUID.randomUUID().toString();
            user.setResetToken(token);
            userRepository.save(user);
            return user;
        });
    }

    public void resetPassword(String token, String newPassword) {
        User user = userRepository.findByResetToken(token).
                orElseThrow(() -> new RuntimeException("Token inválido ou expirado."));
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetToken(null);
        userRepository.save(user);
    }

}
