package com.torchapp.demo.repositories;

import com.torchapp.demo.models.PetShop;
import com.torchapp.demo.models.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {
    Optional<VerificationCode> findByPetShop(PetShop petShop);
}
