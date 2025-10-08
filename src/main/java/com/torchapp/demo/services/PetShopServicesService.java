package com.torchapp.demo.services;

import com.torchapp.demo.dtos.petshopService.PetShopServicesRequest;
import com.torchapp.demo.models.PetShop;
import com.torchapp.demo.models.PetShopServices;
import com.torchapp.demo.repositories.PetShopRepository;
import com.torchapp.demo.repositories.PetShopServicesRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetShopServicesService {
    private final PetShopServicesRepository serviceRepository;
    private final PetShopRepository petShopRepository;

    public PetShopServicesService(PetShopServicesRepository serviceRepository, PetShopRepository petShopRepository) {
        this.serviceRepository = serviceRepository;
        this.petShopRepository = petShopRepository;
    }

    public PetShopServices registerService(PetShopServicesRequest request) {
        PetShop petShop = petShopRepository.findById(request.getId())
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));
        PetShopServices services = new PetShopServices();
        services.setName(request.getName());
        services.setPrice(request.getPrice());
        services.setPetShop(petShop);

        return serviceRepository.save(services);
    }

    public List<PetShopServices> getPetShopServices() {
        return serviceRepository.findAll();
    }

    public PetShopServices getPetShopServiceById(Long id) {
        return serviceRepository.findById(id).orElseThrow(() -> new RuntimeException("Serviço não encontrado"));
    }
}
