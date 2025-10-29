package com.torchapp.demo.services;

import com.torchapp.demo.dtos.petshop.PetShopRegistrationRequest;
import com.torchapp.demo.dtos.petshop.PetShopResponse;
import com.torchapp.demo.dtos.petshop.PetShopUpdateRequest;
import com.torchapp.demo.exceptions.ResourceNotFoundException;
import com.torchapp.demo.mappers.PetShopMapper;
import com.torchapp.demo.models.PetShop;
import com.torchapp.demo.models.User;
import com.torchapp.demo.repositories.PetShopRepository;
import com.torchapp.demo.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PetShopService {

    private final PetShopRepository petShopRepository;
    private final UserRepository userRepository;

    public PetShopService(PetShopRepository petShopRepository, UserRepository userRepository) {
        this.petShopRepository = petShopRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Optional<PetShop> registerPetShop(PetShopRegistrationRequest request) {
        User owner = userRepository.findById(request.getOwnerId())
                .orElseThrow(() -> new ResourceNotFoundException("Dono de PetShop não encontrado."));

        if (!owner.getRole().name().equals("PETSHOP_OWNER")) {
            throw new RuntimeException("Usuário não é um dono de PetShop válido.");
        }

        PetShop petShop = PetShopMapper.toEntity(request);
        petShop.setOwner(owner);
        petShop.setApproved(false); // Aguarda aprovação do admin

        PetShop saved = petShopRepository.save(petShop);

        return Optional.of(saved);
    }

    public List<PetShopResponse> getPetShops() {
        return petShopRepository.findAll().stream()
                .map(PetShopMapper::toResponse)
                .toList();
    }

    private PetShop getPetShopById(Long id) {
        return petShopRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    public PetShopResponse getPetShopByIdResponse(Long id) {
        PetShop petShop = getPetShopById(id);
        return PetShopMapper.toResponse(petShop);
    }

    @Transactional
    public PetShop updatePetShop(Long id, PetShopUpdateRequest petShopUpdateRequest) {
        return petShopRepository.findById(id).map(petShop -> {
            petShop.setName(petShopUpdateRequest.getName());
            petShop.setCep(petShopUpdateRequest.getCep());
            petShop.setState(petShopUpdateRequest.getState());
            petShop.setCity(petShopUpdateRequest.getCity());
            petShop.setNeighborhood(petShopUpdateRequest.getNeighborhood());
            petShop.setStreet(petShopUpdateRequest.getStreet());
            petShop.setNumber(petShopUpdateRequest.getNumber());
            petShop.setAddressComplement(petShopUpdateRequest.getAddressComplement());
            petShop.setPhone(petShopUpdateRequest.getPhone());
            petShop.setEmail(petShopUpdateRequest.getEmail());
            petShop.setCnpj(petShopUpdateRequest.getCnpj());
            return petShopRepository.save(petShop);
        }).orElseThrow(ResourceNotFoundException::new);
    }

    @Transactional
    public void deletePetShop(Long id) {
        if (!petShopRepository.existsById(id)) {
            throw new ResourceNotFoundException();
        }
        petShopRepository.deleteById(id);
    }

    public boolean emailExists(String email) {
        return petShopRepository.findByEmail(email).isPresent();
    }

    public Optional<PetShop> findByEmail(String email) {
        return petShopRepository.findByEmail(email);
    }
}
