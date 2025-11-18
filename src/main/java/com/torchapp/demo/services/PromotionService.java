package com.torchapp.demo.services;

import com.torchapp.demo.dtos.promotion.PromotionRequest;
import com.torchapp.demo.exceptions.BadRequestException;
import com.torchapp.demo.exceptions.ResourceNotFoundException;
import com.torchapp.demo.models.Promotion;
import com.torchapp.demo.repositories.PromotionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PromotionService {

    private final PromotionRepository promotionRepository;

    @Transactional
    public Promotion createPromotion(PromotionRequest request) {
        // Validar se o código do cupom já existe
        if (request.getCouponCode() != null && !request.getCouponCode().isEmpty()) {
            promotionRepository.findByCouponCode(request.getCouponCode())
                    .ifPresent(p -> {
                        throw new BadRequestException("Código de cupom já existe: " + request.getCouponCode());
                    });
        }

        Promotion promotion = new Promotion();
        promotion.setName(request.getName());
        promotion.setDescription(request.getDescription());
        promotion.setValidity(request.getValidity());
        promotion.setCouponCode(request.getCouponCode() != null ? request.getCouponCode().toUpperCase() : null);
        promotion.setDiscountPercent(request.getDiscountPercent());
        promotion.setPetShopId(request.getPetShopId()); // ← ADICIONADO

        return promotionRepository.save(promotion);
    }

    @Transactional(readOnly = true)
    public List<Promotion> getAllPromotions() {
        return promotionRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Promotion getPromotionById(Long id) {
        return promotionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Promoção não encontrada com ID: " + id));
    }

    @Transactional
    public Promotion updatePromotion(Long id, PromotionRequest request) {
        Promotion promotion = promotionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Promoção não encontrada com ID: " + id));

        // Validar se o novo código do cupom já existe (se for diferente do atual)
        if (request.getCouponCode() != null && !request.getCouponCode().isEmpty()) {
            String newCouponCode = request.getCouponCode().toUpperCase();
            if (!newCouponCode.equals(promotion.getCouponCode())) {
                promotionRepository.findByCouponCode(newCouponCode)
                        .ifPresent(p -> {
                            throw new BadRequestException("Código de cupom já existe: " + newCouponCode);
                        });
            }
            promotion.setCouponCode(newCouponCode);
        }

        promotion.setName(request.getName());
        promotion.setDescription(request.getDescription());
        promotion.setValidity(request.getValidity());
        promotion.setDiscountPercent(request.getDiscountPercent());
        promotion.setPetShopId(request.getPetShopId()); // ← ADICIONADO

        return promotionRepository.save(promotion);
    }

    @Transactional
    public void deletePromotion(Long id) {
        if (!promotionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Promoção não encontrada com ID: " + id);
        }
        promotionRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Promotion> getActivePromotions() {
        return promotionRepository.findByValidityGreaterThanEqual(LocalDate.now());
    }

    @Transactional(readOnly = true)
    public List<Promotion> searchPromotionsByName(String name) {
        return promotionRepository.findByNameContainingIgnoreCase(name);
    }

    // ========== MÉTODOS PARA CUPONS ==========
    @Transactional(readOnly = true)
    public Promotion getPromotionByCouponCode(String couponCode) {
        return promotionRepository.findByCouponCode(couponCode.toUpperCase())
                .orElseThrow(() -> new ResourceNotFoundException("Cupom não encontrado: " + couponCode));
    }

    @Transactional(readOnly = true)
    public Promotion validateCoupon(String couponCode) {
        Promotion promotion = getPromotionByCouponCode(couponCode);

        if (!promotion.isValid()) {
            throw new BadRequestException("Este cupom expirou em " + promotion.getValidity());
        }

        return promotion;
    }

    @Transactional(readOnly = true)
    public Double calculateDiscountedPrice(String couponCode, Double originalPrice) {
        Promotion promotion = validateCoupon(couponCode);
        return promotion.calculateDiscount(originalPrice);
    }

    // ========== NOVO: MÉTODO PARA BUSCAR PROMOÇÕES POR PET SHOP ==========
    @Transactional(readOnly = true)
    public List<Promotion> getPromotionsByPetShopId(Long petShopId) {
        return promotionRepository.findByPetShopId(petShopId);
    }
    // =====================================================================
}