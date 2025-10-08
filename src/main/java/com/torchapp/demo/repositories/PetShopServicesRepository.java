package com.torchapp.demo.repositories;

import com.torchapp.demo.models.PetShopServices;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetShopServicesRepository extends JpaRepository<PetShopServices, Long> {
}
