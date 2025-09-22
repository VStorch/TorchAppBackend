package com.torchapp.demo.repositories;

import com.torchapp.demo.models.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet, Long> {
}
