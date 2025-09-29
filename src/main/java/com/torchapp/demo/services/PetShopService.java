package com.torchapp.demo.services;

import com.torchapp.demo.dtos.petshop.PetShopUpdateRequest;
import com.torchapp.demo.models.PetShop;
import com.torchapp.demo.repositories.PetShopRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PetShopService {

    private final PetShopRepository petShopRepository;
    private final PasswordEncoder passwordEncoder;

    public PetShopService(PetShopRepository petShopRepository, PasswordEncoder passwordEncoder) {
        this.petShopRepository = petShopRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<PetShop> registerPetShop(PetShop petShop) {
        petShop.setPassword(passwordEncoder.encode(petShop.getPassword()));
        return Optional.of(petShopRepository.save(petShop));
    }

    public List<PetShop> getPetShops() {
        return petShopRepository.findAll();
    }

    public PetShop getPetShopById(Long id) {
        return petShopRepository.findById(id).orElseThrow(() -> new RuntimeException("Pet Shop não encontrado"));
    }

    public PetShop updatePetShop(Long id, PetShopUpdateRequest petShopUpdateRequest) {
        return petShopRepository.findById(id).map(petShop -> {
            petShop.setName(petShopUpdateRequest.getName());
            petShop.setAddress(petShopUpdateRequest.getAddress());
            petShop.setPhone(petShopUpdateRequest.getPhone());
            petShop.setEmail(petShopUpdateRequest.getEmail());
            petShop.setCnpj(petShopUpdateRequest.getCnpj());
            return petShopRepository.save(petShop);
        }).orElseThrow(() -> new RuntimeException("Pet Shop não encontrado"));
    }

    public void deleteUser(Long id) {
        if (!petShopRepository.existsById(id)) {
            throw new RuntimeException("Pet Shop não encontrado");
        }
        petShopRepository.deleteById(id);
    }

    public Optional<PetShop> login(String email, String rawPassword) {
        return petShopRepository.findByEmail(email)
                .filter(petShop -> passwordEncoder.matches(rawPassword, petShop.getPassword()));
    }

    public boolean emailExists(String email) {
        return petShopRepository.findByEmail(email).isPresent();
    }

}
