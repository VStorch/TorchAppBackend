package com.torchapp.demo.services;

import com.torchapp.demo.dtos.evaluate.EvaluatePetShopRequest;
import com.torchapp.demo.dtos.evaluate.EvaluatePetShopResponse;
import com.torchapp.demo.enums.AppointmentStatus;
import com.torchapp.demo.exceptions.BadRequestException;
import com.torchapp.demo.exceptions.ResourceNotFoundException;
import com.torchapp.demo.mappers.EvaluatePetShopMapper;
import com.torchapp.demo.models.EvaluatePetShop;
import com.torchapp.demo.models.PetShop;
import com.torchapp.demo.models.User;
import com.torchapp.demo.repositories.AppointmentRepository;
import com.torchapp.demo.repositories.EvaluatePetShopRepository;
import com.torchapp.demo.repositories.PetShopRepository;
import com.torchapp.demo.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EvaluatePetShopService {

    private final EvaluatePetShopRepository evaluateRepository;
    private final AppointmentRepository appointmentRepository;
    private final PetShopRepository petShopRepository;
    private final UserRepository userRepository;

    public EvaluatePetShopService(EvaluatePetShopRepository evaluateRepository, AppointmentRepository appointmentRepository, PetShopRepository petShopRepository, UserRepository userRepository) {
        this.evaluateRepository = evaluateRepository;
        this.appointmentRepository = appointmentRepository;
        this.petShopRepository = petShopRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public EvaluatePetShopResponse evaluatePetShop(EvaluatePetShopRequest request) {
        PetShop petShop = petShopRepository.findById(request.getPetShopId())
                .orElseThrow(() -> new ResourceNotFoundException("PetShop não encontrado."));
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado."));

        evaluateRepository.findByUserIdAndPetShopId(user.getId(), petShop.getId())
                .ifPresent(e -> {
                    throw new BadRequestException("Usuário já avaliou este PetShop.");
                });

        boolean hasCompletedAppointment = appointmentRepository.existsByUserIdAndPetShopIdAndStatus(
                user.getId(),
                petShop.getId(),
                AppointmentStatus.COMPLETED
        );

        if (!hasCompletedAppointment) {
            throw new BadRequestException("Usuário não pode avaliar um PetShop sem ter completado um agendamento.");
        }

        EvaluatePetShop evaluation = EvaluatePetShopMapper.toEntity(request, petShop, user);
        evaluateRepository.save(evaluation);

        updatePetShopAverage(petShop, request.getRating());

        return EvaluatePetShopMapper.toResponse(evaluation);
    }

    private void updatePetShopAverage(PetShop petShop, int newRating) {
        int total = petShop.getTotalRatings();
        double average = petShop.getAverageRating();

        double newAverage = total == 0
                ? newRating
                : ((average * total) + newRating) / (total + 1);

        petShop.setTotalRatings(total + 1);
        petShop.setAverageRating(newAverage);
        petShopRepository.save(petShop);
    }

    @Transactional(readOnly = true)
    public List<EvaluatePetShopResponse> listByPetShop(Long id) {
        PetShop petShop = petShopRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PetShop não encontrado."));
        List<EvaluatePetShop> evaluations = evaluateRepository.findAllByPetShopId(petShop.getId());

        return evaluations.stream()
                .map(EvaluatePetShopMapper::toResponse)
                .toList();
    }
}
