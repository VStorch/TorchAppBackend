package com.torchapp.demo.repositories;

import com.torchapp.demo.models.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long> {

    List<Promotion> findByValidityAfter(LocalDate date);

    List<Promotion> findByValidityGreaterThanEqual(LocalDate date);

    @Query("SELECT p FROM Promotion p WHERE p.validity >= :date")
    List<Promotion> findActivePromotions(@Param("date") LocalDate date);

    List<Promotion> findByNameContainingIgnoreCase(String name);

    Optional<Promotion> findByCouponCode(String couponCode);

    boolean existsByCouponCode(String couponCode);

    // ========== NOVO: BUSCAR POR PET SHOP ==========
    List<Promotion> findByPetShopId(Long petShopId); // ← ADICIONAR
    // ===============================================
}