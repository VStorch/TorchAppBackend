package com.torchapp.demo.repositories;

import com.torchapp.demo.models.EvaluatePetShop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EvaluatePetShopRepository extends JpaRepository<EvaluatePetShop, Long> {
    Optional<EvaluatePetShop> findByUserIdAndPetShopId(Long userId, Long petShopId);
    List<EvaluatePetShop> findAllByPetShopId(Long petShopId);
}
