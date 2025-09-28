package com.torchapp.demo.repositories;

import com.torchapp.demo.models.PetShop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetShopRepository extends JpaRepository<PetShop, Long> {
}
