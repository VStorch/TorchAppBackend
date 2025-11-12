package com.torchapp.demo.controllers;

import com.torchapp.demo.dtos.petShopnIformation.PetShopInformationRequestDTO;
import com.torchapp.demo.dtos.petShopnIformation.PetShopInformationResponseDTO;
import com.torchapp.demo.services.PetShopInformationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/petshop-information")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PetShopInformationController {

    private final PetShopInformationService petShopInformationService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> createPetShopInformation(@RequestBody PetShopInformationRequestDTO dto) {
        try {
            PetShopInformationResponseDTO response = petShopInformationService.createPetShopInformation(dto);
            Map<String, Object> result = new HashMap<>();
            result.put("success", true);
            result.put("message", "Informações do Pet Shop criadas com sucesso!");
            result.put("data", response);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (IllegalStateException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getPetShopInformationById(@PathVariable Long id) {
        PetShopInformationResponseDTO response = petShopInformationService.getPetShopInformationById(id);
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("data", response);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Map<String, Object>> getPetShopInformationByUserId(@PathVariable Long userId) {
        PetShopInformationResponseDTO response = petShopInformationService.getPetShopInformationByUserId(userId);
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("data", response);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updatePetShopInformation(
            @PathVariable Long id,
            @RequestBody PetShopInformationRequestDTO dto) {
        PetShopInformationResponseDTO response = petShopInformationService.updatePetShopInformation(id, dto);
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("message", "Informações do Pet Shop atualizadas com sucesso!");
        result.put("data", response);
        return ResponseEntity.ok(result);
    }

    @PostMapping(value = "/{id}/logo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, Object>> uploadLogo(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                Map<String, Object> error = new HashMap<>();
                error.put("success", false);
                error.put("message", "Arquivo não pode estar vazio");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
            }

            // Validar tipo de arquivo
            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                Map<String, Object> error = new HashMap<>();
                error.put("success", false);
                error.put("message", "Arquivo deve ser uma imagem");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
            }

            String logoUrl = petShopInformationService.uploadLogo(id, file);
            Map<String, Object> result = new HashMap<>();
            result.put("success", true);
            result.put("message", "Logo enviada com sucesso!");
            result.put("logoUrl", logoUrl);
            return ResponseEntity.ok(result);
        } catch (IOException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", "Erro ao fazer upload da logo: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deletePetShopInformation(@PathVariable Long id) {
        petShopInformationService.deletePetShopInformation(id);
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("message", "Informações do Pet Shop excluídas com sucesso!");
        return ResponseEntity.ok(result);
    }
}