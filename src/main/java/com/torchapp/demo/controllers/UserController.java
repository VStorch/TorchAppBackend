package com.torchapp.demo.controllers;

import com.torchapp.demo.models.User;
import com.torchapp.demo.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Endpoint para criar um usuário
    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User savedUser = userService.registerUser(user);
        return ResponseEntity.status(201).body(savedUser); // 201 Created
    }

    // Endpoint para listar todos os usuários
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getUsers();
    }

    // Endpoint para buscar um usuário pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        try {
            User user = userService.getUserById(id);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint para atualizar um usuário
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }

    // Endpoint para deletar um usuário
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } catch(RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint para logar o usuário
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginRequest) {
        return userService.login(loginRequest.getEmail(), loginRequest.getPassword())
                .map(ResponseEntity :: ok)
                .orElse(ResponseEntity.status(401).body("Credenciais inválidas"));
    }
    // Ajustes necessários no método login



    // Endpoint para verificar se email já existe
    @GetMapping("/email/{email}")
    public ResponseEntity<Void> emailExists(@PathVariable String email) {
        boolean exists = userService.emailExists(email);
        return exists
                ? ResponseEntity.ok().build() // 200 Ok
                : ResponseEntity.notFound().build(); // 404 Not Found
    }
}
