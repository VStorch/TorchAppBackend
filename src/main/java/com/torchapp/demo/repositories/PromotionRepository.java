package com.torchapp.demo.repositories;

import com.torchapp.demo.models.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long> {

    List<Promotion> findByValidityAfter(LocalDate date);

    List<Promotion> findByNameContainingIgnoreCase(String name);
}