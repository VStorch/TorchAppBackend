package com.torchapp.demo.controllers;

import com.torchapp.demo.dtos.LoginRequest;
import com.torchapp.demo.dtos.user.*;
import com.torchapp.demo.dtos.petshop.PetShopResponse;
import com.torchapp.demo.exceptions.BadRequestException;
import com.torchapp.demo.mappers.UserMapper;
import com.torchapp.demo.mappers.PetShopMapper;
import com.torchapp.demo.models.PetShop;
import com.torchapp.demo.models.User;
import com.torchapp.demo.repositories.PetShopRepository;
import com.torchapp.demo.services.EmailService;
import com.torchapp.demo.services.UserService;
import com.torchapp.demo.services.VerificationCodeService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final EmailService emailService;
    private final VerificationCodeService verificationCodeService;
    private final PetShopRepository petShopRepository;

    public UserController(UserService userService,
                          EmailService emailService,
                          VerificationCodeService verificationCodeService,
                          PetShopRepository petShopRepository) {
        this.userService = userService;
        this.emailService = emailService;
        this.verificationCodeService = verificationCodeService;
        this.petShopRepository = petShopRepository;
    }

    // Endpoint para criar um usuário
    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody UserRegistrationRequest userRegistrationRequest) throws MessagingException, UnsupportedOperationException {
        User user = UserMapper.toEntity(userRegistrationRequest);

        return userService.registerUser(user)
                .map(savedUser -> {
                    emailService.sendMailWithInline(savedUser);
                    return ResponseEntity.status(201).body(UserMapper.toResponse(savedUser));
                })
                .orElse(ResponseEntity.status(400).body(null));
    }

    @PostMapping("/petshop-owner")
    public ResponseEntity<?> registerOwner(@Valid @RequestBody OwnerRegistrationRequest request) {
        User user = UserMapper.toEntityOwner(request);
        Optional<User> saved = userService.registerPetShopOwner(user, verificationCodeService);

        return saved.map(savedUser ->
                ResponseEntity.status(201).body(Map.of(
                        "id", savedUser.getId(),
                        "message", "Dono de PetShop cadastrado com sucesso."
                ))
        ).orElse(
                ResponseEntity.status(400).body(Map.of(
                        "error", "Erro ao cadastrar dono de PetShop"
                ))
        );
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<PetShopResponse> getPetShopByOwnerId(@PathVariable Long ownerId) {
        return petShopRepository.findByOwnerId(ownerId)
                .map(petShop -> ResponseEntity.ok(PetShopMapper.toResponse(petShop)))
                .orElse(ResponseEntity.notFound().build());
    }

    // Endpoint para listar todos os usuários
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    // Endpoint para buscar um usuário pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserByIdResponse(id));
    }

    // Endpoint para atualizar um usuário
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @RequestBody UserUpdateRequest userUpdateRequest) {
        User updateUser = userService.updateUser(id, userUpdateRequest);
        return ResponseEntity.ok(UserMapper.toResponse(updateUser));
    }

    @PutMapping("/petshop-owner/{id}")
    public ResponseEntity<OwnerResponse> updateOwner(@PathVariable Long id, @RequestBody OwnerUpdateRequest request) {
        User updateOwner = userService.updateOwner(id, request);
        return ResponseEntity.ok(UserMapper.toResponseOwner(updateOwner));
    }

    // Endpoint para deletar um usuário
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoint para logar o usuário
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        return userService.login(loginRequest.getEmail(), loginRequest.getPassword())
                .<ResponseEntity<?>>map(user -> ResponseEntity.ok(
                        UserMapper.toResponse(user)
                ))
                .orElseThrow(() -> new BadRequestException("Credenciais inválidas."));
    }

    // Endpoint para verificar se email já existe
    @GetMapping("/email/{email}")
    public ResponseEntity<Void> emailExists(@PathVariable String email) {
        boolean exists = userService.emailExists(email);
        return exists
                ? ResponseEntity.ok().build() // 200 Ok
                : ResponseEntity.notFound().build(); // 404 Not Found
    }

    // Endpoint para solicitar reset
    @PostMapping("/reset-password/request")
    public ResponseEntity<?> requestPasswordReset(@Valid @RequestBody PasswordResetRequest request){
        return userService.generatePasswordResetToken(request.getEmail())
                .map(user -> {
                    emailService.sendRedirectMail(user);
                    return ResponseEntity.ok("Se o email existir, um link de redefinição foi enviado.");
                })
                .orElse(ResponseEntity.ok("Se o email existir, um link de redefinição foi enviado."));
    }

    // Endpoint para confirmar reset
    @PostMapping("/reset-password/confirm")
    public ResponseEntity<?> confirmPasswordReset(@Valid @RequestBody PasswordResetConfirmRequest request) {
        userService.resetPassword(
                request.getEmail(),
                request.getToken(),
                request.getNewPassword()
        );
        return ResponseEntity.ok("Senha redefinida com sucesso.");
    }
}