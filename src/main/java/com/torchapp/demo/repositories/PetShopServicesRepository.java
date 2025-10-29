package com.torchapp.demo.repositories;

import com.torchapp.demo.models.PetShopServices;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PetShopServicesRepository extends JpaRepository<PetShopServices, Long> {
    List<PetShopServices> findByPetShopId(Long petShopId);
}
