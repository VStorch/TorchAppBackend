package com.torchapp.demo.repositories;


import com.torchapp.demo.models.PetShopInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PetShopInformationRepository extends JpaRepository<PetShopInformation, Long> {

    Optional<PetShopInformation> findByUserId(Long userId);

    boolean existsByUserId(Long userId);
}