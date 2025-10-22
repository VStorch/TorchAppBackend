package com.torchapp.demo.services;

import com.torchapp.demo.dtos.appointment.AppointmentRequest;
import com.torchapp.demo.dtos.appointment.AppointmentResponse;
import com.torchapp.demo.enums.AppointmentStatus;
import com.torchapp.demo.exceptions.ResourceNotFoundException;
import com.torchapp.demo.mappers.AppointmentMapper;
import com.torchapp.demo.models.*;
import com.torchapp.demo.repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final PetRepository petRepository;
    private final PetShopRepository petShopRepository;
    private final PetShopServicesRepository petShopServicesRepository;
    private final AvailableSlotRepository availableSlotRepository;

    public AppointmentService(AppointmentRepository appointmentRepository, UserRepository userRepository, PetRepository petRepository, PetShopRepository petShopRepository, PetShopServicesRepository petShopServicesRepository, AvailableSlotRepository availableSlotRepository) {
        this.appointmentRepository = appointmentRepository;
        this.userRepository = userRepository;
        this.petRepository = petRepository;
        this.petShopRepository = petShopRepository;
        this.petShopServicesRepository = petShopServicesRepository;
        this.availableSlotRepository = availableSlotRepository;
    }

    @Transactional
    public AppointmentResponse createAppointment(AppointmentRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado."));
        Pet pet = petRepository.findById(request.getPetId())
                .orElseThrow(() -> new ResourceNotFoundException("Pet não encontrado."));
        PetShop petShop = petShopRepository.findById(request.getPetShopId())
                .orElseThrow(() -> new ResourceNotFoundException("PetShop não encontrado."));
        PetShopServices service = petShopServicesRepository.findById(request.getServiceId())
                .orElseThrow(() -> new ResourceNotFoundException("Serviço não encontrado."));
        AvailableSlot slot = availableSlotRepository.findById(request.getSlotId())
                .orElseThrow(() -> new ResourceNotFoundException("Horário não encontrado."));

        if (!service.getPetShop().getId().equals(petShop.getId())) {
            throw new IllegalArgumentException("Serviço não pertence a este PetShop.");
        }
        if (!pet.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("Pet não pertence a este Usuário.");
        }
        if (slot.isBooked()) {
            throw new IllegalStateException("Esse horário já foi reservado.");
        }

        slot.setBooked(true);

        Appointment appointment = new Appointment();
        appointment.setDate(slot.getDate());
        appointment.setTime(slot.getStartTime());
        appointment.setStatus(AppointmentStatus.PENDING);
        appointment.setUser(user);
        appointment.setPet(pet);
        appointment.setPetShop(petShop);
        appointment.setService(service);

        availableSlotRepository.save(slot);
        Appointment saved = appointmentRepository.save(appointment);
        return AppointmentMapper.toResponse(saved);
    }

    public List<AppointmentResponse> getAppointments() {
        return appointmentRepository.findAll().stream()
                .map(AppointmentMapper::toResponse).toList();
    }

    public AppointmentResponse getAppointmentById(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agendamento não encontrado."));
        return AppointmentMapper.toResponse(appointment);
    }

}
