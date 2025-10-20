package com.torchapp.demo.services;

import com.torchapp.demo.dtos.petshopService.PetShopServicesRequest;
import com.torchapp.demo.exceptions.ResourceNotFoundException;
import com.torchapp.demo.models.PetShop;
import com.torchapp.demo.models.PetShopServices;
import com.torchapp.demo.repositories.PetShopRepository;
import com.torchapp.demo.repositories.PetShopServicesRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class PetShopServicesService {
    private final PetShopServicesRepository serviceRepository;
    private final PetShopRepository petShopRepository;

    public PetShopServicesService(PetShopServicesRepository serviceRepository, PetShopRepository petShopRepository) {
        this.serviceRepository = serviceRepository;
        this.petShopRepository = petShopRepository;
    }

    @Transactional
    public PetShopServices registerService(PetShopServicesRequest request) {
        PetShop petShop = petShopRepository.findById(request.getPetShopId())
                .orElseThrow(RuntimeException::new);
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
        return serviceRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    @Transactional
    public PetShopServices updateService(Long id, PetShopServices newServicesData) {
        return serviceRepository.findById(id).map(service -> {
            service.setName(newServicesData.getName());
            return serviceRepository.save(service);
        }).orElseThrow(ResourceNotFoundException::new);
    }

    @Transactional
    public void deleteService(Long id) {
        if (!serviceRepository.existsById(id)) {
            throw new ResourceNotFoundException();
        }
        serviceRepository.deleteById(id);
    }
}
