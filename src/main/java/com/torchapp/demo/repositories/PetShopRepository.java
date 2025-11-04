package com.torchapp.demo.repositories;

import com.torchapp.demo.models.PetShop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PetShopRepository extends JpaRepository<PetShop, Long> {
    Optional<PetShop> findByEmail(String email);
    Optional<PetShop> findByOwnerId(Long ownerId);

}



