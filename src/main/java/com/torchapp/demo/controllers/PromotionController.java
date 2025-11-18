package com.torchapp.demo.controllers;

import com.torchapp.demo.dtos.promotion.PromotionRequest;
import com.torchapp.demo.models.Promotion;
import com.torchapp.demo.services.PromotionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    // ========== NOVOS ENDPOINTS PARA CUPONS ==========
    @GetMapping("/coupon/{couponCode}")
    public ResponseEntity<Promotion> getPromotionByCouponCode(@PathVariable String couponCode) {
        Promotion promotion = promotionService.getPromotionByCouponCode(couponCode);
        return ResponseEntity.ok(promotion);
    }

    @PostMapping("/validate-coupon")
    public ResponseEntity<Map<String, Object>> validateCoupon(@RequestParam String couponCode) {
        Promotion promotion = promotionService.validateCoupon(couponCode);

        Map<String, Object> response = new HashMap<>();
        response.put("valid", true);
        response.put("promotion", promotion);
        response.put("discountPercent", promotion.getDiscountPercent());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/calculate-discount")
    public ResponseEntity<Map<String, Object>> calculateDiscount(
            @RequestParam String couponCode,
            @RequestParam Double originalPrice) {

        Promotion promotion = promotionService.validateCoupon(couponCode);
        Double discountedPrice = promotion.calculateDiscount(originalPrice);
        Double discountAmount = originalPrice - discountedPrice;

        Map<String, Object> response = new HashMap<>();
        response.put("originalPrice", originalPrice);
        response.put("discountPercent", promotion.getDiscountPercent());
        response.put("discountAmount", discountAmount);
        response.put("finalPrice", discountedPrice);
        response.put("couponCode", couponCode);

        return ResponseEntity.ok(response);
    }
    // ================================================
}