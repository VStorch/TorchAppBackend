package com.torchapp.demo.controllers;

import com.torchapp.demo.dtos.promotion.PromotionRequest;
import com.torchapp.demo.models.Promotion;
import com.torchapp.demo.services.PromotionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/promotions")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PromotionController {

    private final PromotionService promotionService;

    @PostMapping
    public ResponseEntity<Promotion> createPromotion(@Valid @RequestBody PromotionRequest request) {
        Promotion promotion = promotionService.createPromotion(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(promotion);
    }

    @GetMapping
    public ResponseEntity<List<Promotion>> getAllPromotions() {
        List<Promotion> promotions = promotionService.getAllPromotions();
        return ResponseEntity.ok(promotions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Promotion> getPromotionById(@PathVariable Long id) {
        Promotion promotion = promotionService.getPromotionById(id);
        return ResponseEntity.ok(promotion);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Promotion> updatePromotion(
            @PathVariable Long id,
            @Valid @RequestBody PromotionRequest request) {
        Promotion promotion = promotionService.updatePromotion(id, request);
        return ResponseEntity.ok(promotion);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePromotion(@PathVariable Long id) {
        promotionService.deletePromotion(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/active")
    public ResponseEntity<List<Promotion>> getActivePromotions() {
        List<Promotion> promotions = promotionService.getActivePromotions();
        return ResponseEntity.ok(promotions);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Promotion>> searchPromotions(@RequestParam String name) {
        List<Promotion> promotions = promotionService.searchPromotionsByName(name);
        return ResponseEntity.ok(promotions);
    }
}