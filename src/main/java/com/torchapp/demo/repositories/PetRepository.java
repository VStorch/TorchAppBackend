package com.torchapp.demo.repositories;

import com.torchapp.demo.models.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findByUserId(Long userId);
}
