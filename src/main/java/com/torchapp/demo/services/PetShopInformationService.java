package com.torchapp.demo.services;

import com.torchapp.demo.dtos.petShopnIformation.PetShopInformationRequestDTO;
import com.torchapp.demo.dtos.petShopnIformation.PetShopInformationResponseDTO;
import com.torchapp.demo.exceptions.ResourceNotFoundException;
import com.torchapp.demo.models.PetShopInformation;
import com.torchapp.demo.repositories.PetShopInformationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PetShopInformationService {

    private final PetShopInformationRepository petShopInformationRepository;
    private static final String UPLOAD_DIR = "uploads/logos/";

    @Transactional
    public PetShopInformationResponseDTO createPetShopInformation(PetShopInformationRequestDTO dto) {
        if (petShopInformationRepository.existsByUserId(dto.getUserId())) {
            throw new IllegalStateException("Usuário já possui um Pet Shop cadastrado");
        }

        PetShopInformation petShopInformation = new PetShopInformation();
        mapDtoToEntity(dto, petShopInformation);

        PetShopInformation saved = petShopInformationRepository.save(petShopInformation);
        return mapEntityToDto(saved);
    }

    @Transactional(readOnly = true)
    public PetShopInformationResponseDTO getPetShopInformationById(Long id) {
        PetShopInformation petShopInformation = petShopInformationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pet Shop não encontrado com ID: " + id));
        return mapEntityToDto(petShopInformation);
    }

    @Transactional(readOnly = true)
    public PetShopInformationResponseDTO getPetShopInformationByUserId(Long userId) {
        PetShopInformation petShopInformation = petShopInformationRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Pet Shop não encontrado para o usuário ID: " + userId));
        return mapEntityToDto(petShopInformation);
    }

    @Transactional
    public PetShopInformationResponseDTO updatePetShopInformation(Long id, PetShopInformationRequestDTO dto) {
        PetShopInformation petShopInformation = petShopInformationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pet Shop não encontrado com ID: " + id));

        mapDtoToEntity(dto, petShopInformation);

        PetShopInformation updated = petShopInformationRepository.save(petShopInformation);
        return mapEntityToDto(updated);
    }

    @Transactional
    public String uploadLogo(Long petShopInformationId, MultipartFile file) throws IOException {
        PetShopInformation petShopInformation = petShopInformationRepository.findById(petShopInformationId)
                .orElseThrow(() -> new ResourceNotFoundException("Pet Shop não encontrado com ID: " + petShopInformationId));

        // Criar diretório se não existir
        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Deletar logo antiga se existir
        if (petShopInformation.getLogoUrl() != null) {
            try {
                Path oldFile = Paths.get(petShopInformation.getLogoUrl());
                Files.deleteIfExists(oldFile);
            } catch (IOException e) {
                // Log error but continue
                System.err.println("Erro ao deletar logo antiga: " + e.getMessage());
            }
        }

        // Gerar nome único para o arquivo
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename != null ?
                originalFilename.substring(originalFilename.lastIndexOf(".")) : ".jpg";
        String filename = UUID.randomUUID().toString() + extension;

        Path filePath = uploadPath.resolve(filename);
        Files.copy(file.getInputStream(), filePath);

        String logoUrl = UPLOAD_DIR + filename;
        petShopInformation.setLogoUrl(logoUrl);
        petShopInformationRepository.save(petShopInformation);

        return logoUrl;
    }

    @Transactional
    public void deletePetShopInformation(Long id) {
        PetShopInformation petShopInformation = petShopInformationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pet Shop não encontrado com ID: " + id));

        // Deletar logo se existir
        if (petShopInformation.getLogoUrl() != null) {
            try {
                Path file = Paths.get(petShopInformation.getLogoUrl());
                Files.deleteIfExists(file);
            } catch (IOException e) {
                // Log error but continue
                System.err.println("Erro ao deletar logo: " + e.getMessage());
            }
        }

        petShopInformationRepository.delete(petShopInformation);
    }

    // Método corrigido - usa setters ao invés de construtor
    private void mapDtoToEntity(PetShopInformationRequestDTO dto, PetShopInformation entity) {
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setServices(dto.getServices());
        entity.setInstagram(dto.getInstagram());
        entity.setFacebook(dto.getFacebook());
        entity.setWebsite(dto.getWebsite());
        entity.setWhatsapp(dto.getWhatsapp());
        entity.setCommercialPhone(dto.getCommercialPhone());
        entity.setCommercialEmail(dto.getCommercialEmail());
        entity.setUserId(dto.getUserId());
    }

    // Método corrigido - usa setters ao invés de construtor com argumentos
    private PetShopInformationResponseDTO mapEntityToDto(PetShopInformation entity) {
        PetShopInformationResponseDTO dto = new PetShopInformationResponseDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setLogoUrl(entity.getLogoUrl());
        dto.setServices(entity.getServices());
        dto.setInstagram(entity.getInstagram());
        dto.setFacebook(entity.getFacebook());
        dto.setWebsite(entity.getWebsite());
        dto.setWhatsapp(entity.getWhatsapp());
        dto.setCommercialPhone(entity.getCommercialPhone());
        dto.setCommercialEmail(entity.getCommercialEmail());
        dto.setUserId(entity.getUserId());
        return dto;
    }
}