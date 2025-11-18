package com.torchapp.demo.services;

import com.torchapp.demo.dtos.appointment.AppointmentRequest;
import com.torchapp.demo.dtos.appointment.AppointmentResponse;
import com.torchapp.demo.enums.AppointmentStatus;
import com.torchapp.demo.exceptions.BadRequestException;
import com.torchapp.demo.exceptions.ResourceNotFoundException;
import com.torchapp.demo.mappers.AppointmentMapper;
import com.torchapp.demo.models.*;
import com.torchapp.demo.repositories.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final PetRepository petRepository;
    private final PetShopRepository petShopRepository;
    private final PetShopServicesRepository petShopServicesRepository;
    private final AvailableSlotRepository availableSlotRepository;
    private final PromotionRepository promotionRepository;

    public AppointmentService(
            AppointmentRepository appointmentRepository,
            UserRepository userRepository,
            PetRepository petRepository,
            PetShopRepository petShopRepository,
            PetShopServicesRepository petShopServicesRepository,
            AvailableSlotRepository availableSlotRepository,
            PromotionRepository promotionRepository) {
        this.appointmentRepository = appointmentRepository;
        this.userRepository = userRepository;
        this.petRepository = petRepository;
        this.petShopRepository = petShopRepository;
        this.petShopServicesRepository = petShopServicesRepository;
        this.availableSlotRepository = availableSlotRepository;
        this.promotionRepository = promotionRepository;
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

        Optional<Appointment> existingAppointment = appointmentRepository
                .findBySlotIdAndStatus(request.getSlotId(), AppointmentStatus.PENDING);

        if (existingAppointment.isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Este horário já está agendado. Por favor, escolha outro horário."
            );
        }

        if (slot.isBooked()) {
            throw new IllegalStateException("Esse horário já foi reservado.");
        }

        // ========== PROCESSAMENTO DO CUPOM DE DESCONTO ==========
        String couponCode = null;
        BigDecimal discountPercent = null;
        BigDecimal finalPrice = service.getPrice();

        if (request.getCouponCode() != null && !request.getCouponCode().trim().isEmpty()) {
            String inputCoupon = request.getCouponCode().trim().toUpperCase();

            System.out.println("🎟️ Processando cupom: " + inputCoupon);

            Optional<Promotion> promotionOpt = promotionRepository.findByCouponCode(inputCoupon);

            if (promotionOpt.isEmpty()) {
                throw new BadRequestException("Cupom não encontrado: " + inputCoupon);
            }

            Promotion promotion = promotionOpt.get();

            // Validar se o cupom ainda é válido
            if (!promotion.isValid()) {
                throw new BadRequestException("Este cupom expirou em " + promotion.getValidity());
            }

            // Validar se o cupom pertence ao pet shop correto (se especificado)
            if (promotion.getPetShopId() != null) {
                if (!promotion.getPetShopId().equals(request.getPetShopId())) {
                    throw new BadRequestException("Este cupom não é válido para este pet shop");
                }
            } else {
                // Se a promoção não tem petShopId definido, rejeitar
                throw new BadRequestException("Este cupom não está vinculado a nenhum pet shop");
            }

            // Aplicar desconto
            couponCode = promotion.getCouponCode();
            discountPercent = BigDecimal.valueOf(promotion.getDiscountPercent());

            // Converter Double para BigDecimal corretamente
            Double discountedValue = promotion.calculateDiscount(service.getPrice().doubleValue());
            finalPrice = BigDecimal.valueOf(discountedValue);

            System.out.println("✅ CUPOM APLICADO COM SUCESSO:");
            System.out.println("   Código: " + couponCode);
            System.out.println("   Desconto: " + discountPercent + "%");
            System.out.println("   Preço Original: R$ " + service.getPrice());
            System.out.println("   Preço Final: R$ " + finalPrice);
        }
        // =======================================================

        slot.setBooked(true);

        Appointment appointment = new Appointment();
        appointment.setDate(slot.getDate());
        appointment.setTime(slot.getStartTime());
        appointment.setStatus(AppointmentStatus.PENDING);
        appointment.setUser(user);
        appointment.setPet(pet);
        appointment.setPetShop(petShop);
        appointment.setService(service);
        appointment.setSlot(slot);

        // ========== ADICIONAR DADOS DO CUPOM ==========
        appointment.setCouponCode(couponCode);
        appointment.setDiscountPercent(discountPercent);
        appointment.setFinalPrice(finalPrice);
        // ==============================================

        Appointment saved = appointmentRepository.save(appointment);

        System.out.println("✅ AGENDAMENTO CRIADO:");
        System.out.println("   ID: " + saved.getId());
        System.out.println("   Serviço: " + saved.getService().getName());
        System.out.println("   Preço Original: R$ " + saved.getService().getPrice());
        System.out.println("   Cupom: " + saved.getCouponCode());
        System.out.println("   Desconto: " + saved.getDiscountPercent() + "%");
        System.out.println("   Preço Final: R$ " + saved.getFinalPrice());

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

    @Transactional
    public void cancelAppointment(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agendamento não encontrado."));

        appointment.setStatus(AppointmentStatus.CANCELLED);
        appointmentRepository.save(appointment);

        AvailableSlot slot = appointment.getSlot();
        if (slot != null) {
            slot.setBooked(false);
            availableSlotRepository.save(slot);
        }
    }

    @Transactional
    public void deleteAppointment(Long id) {
        if(!appointmentRepository.existsById(id)) {
            throw new ResourceNotFoundException();
        }
        appointmentRepository.deleteById(id);
    }

    @Transactional
    public void completeAppointment(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agendamento não encontrado."));

        if (appointment.getStatus() == AppointmentStatus.CANCELLED) {
            throw new BadRequestException("Não é possível concluir um agendamento cancelado.");
        }
        if (appointment.getStatus() == AppointmentStatus.COMPLETED) {
            throw new BadRequestException("Este agendamento já foi concluído");
        }

        appointment.setStatus(AppointmentStatus.COMPLETED);
        appointmentRepository.save(appointment);
    }

    @Transactional
    public void confirmAppointment(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agendamento não encontrado."));

        if (appointment.getStatus() == AppointmentStatus.CANCELLED) {
            throw new BadRequestException("Não é possível confirmar um agendamento cancelado.");
        }
        if (appointment.getStatus() == AppointmentStatus.COMPLETED) {
            throw new BadRequestException("Este agendamento já foi concluído");
        }
        if (appointment.getStatus() == AppointmentStatus.CONFIRMED) {
            throw new BadRequestException("Este agendamento já está confirmado");
        }

        appointment.setStatus(AppointmentStatus.CONFIRMED);
        appointmentRepository.save(appointment);
    }
}