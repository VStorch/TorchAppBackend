package com.torchapp.demo.repositories;

import com.torchapp.demo.enums.AppointmentStatus;
import com.torchapp.demo.models.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    // Verificar se usuário tem agendamento completo no pet shop
    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM Appointment a " +
            "WHERE a.user.id = :userId AND a.petShop.id = :petShopId AND a.status = :status")
    boolean existsByUserIdAndPetShopIdAndStatus(
            @Param("userId") Long userId,
            @Param("petShopId") Long petShopId,
            @Param("status") AppointmentStatus status);

    // Verificar se slot está ocupado ignorando um status específico
    @Query("SELECT a FROM Appointment a WHERE a.slot.id = :slotId AND a.status <> :status")
    Optional<Appointment> findBySlotIdAndStatusNot(
            @Param("slotId") Long slotId,
            @Param("status") AppointmentStatus status);

    // Verificar slot com status específico
    @Query("SELECT a FROM Appointment a WHERE a.slot.id = :slotId AND a.status = :status")
    Optional<Appointment> findBySlotIdAndStatus(
            @Param("slotId") Long slotId,
            @Param("status") AppointmentStatus status);

    // ========== QUERIES PARA CUPONS ==========

    // Buscar agendamentos por código de cupom
    List<Appointment> findByCouponCode(String couponCode);

    // Buscar agendamentos com desconto
    @Query("SELECT a FROM Appointment a WHERE a.couponCode IS NOT NULL AND a.discountPercent > 0")
    List<Appointment> findAllWithDiscount();

    // Contar agendamentos com cupom por pet shop
    @Query("SELECT COUNT(a) FROM Appointment a WHERE a.petShop.id = :petShopId AND a.couponCode IS NOT NULL")
    Long countAppointmentsWithCouponByPetShop(@Param("petShopId") Long petShopId);

    // Buscar agendamentos por pet shop com cupom específico
    @Query("SELECT a FROM Appointment a WHERE a.petShop.id = :petShopId AND a.couponCode = :couponCode")
    List<Appointment> findByPetShopIdAndCouponCode(@Param("petShopId") Long petShopId, @Param("couponCode") String couponCode);
}