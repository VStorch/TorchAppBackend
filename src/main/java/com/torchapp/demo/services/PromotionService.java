package com.torchapp.demo.services;


import com.torchapp.demo.dtos.petshop.PromotionRequest;
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
        Promotion promotion = new Promotion();
        promotion.setName(request.getName());
        promotion.setDescription(request.getDescription());
        promotion.setValidity(request.getValidity());

        return promotionRepository.save(promotion);
    }

    @Transactional(readOnly = true)
    public List<Promotion> getAllPromotions() {
        return promotionRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Promotion getPromotionById(Long id) {
        return promotionRepository.findById(id).orElse(null);
    }

    @Transactional
    public Promotion updatePromotion(Long id, PromotionRequest request) {
        Promotion promotion = promotionRepository.findById(id).orElse(null);
        if (promotion == null) {
            return null;
        }

        promotion.setName(request.getName());
        promotion.setDescription(request.getDescription());
        promotion.setValidity(request.getValidity());

        return promotionRepository.save(promotion);
    }

    @Transactional
    public void deletePromotion(Long id) {
        promotionRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Promotion> getActivePromotions() {
        return promotionRepository.findByValidityAfter(LocalDate.now());
    }

    @Transactional(readOnly = true)
    public List<Promotion> searchPromotionsByName(String name) {
        return promotionRepository.findByNameContainingIgnoreCase(name);
    }
}