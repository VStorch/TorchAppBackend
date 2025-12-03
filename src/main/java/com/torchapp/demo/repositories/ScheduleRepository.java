package com.torchapp.demo.repositories;


import com.torchapp.demo.enums.DayOfWeek;
import com.torchapp.demo.models.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    // Buscar todos os horários de um Pet Shop
    List<Schedule> findByPetShopIdAndIsActiveTrue(Long petShopId);

    // Buscar todos os horários (incluindo inativos)
    List<Schedule> findByPetShopId(Long petShopId);

    // Buscar horário específico de um dia
    Optional<Schedule> findByPetShopIdAndDayOfWeekAndIsActiveTrue(
            Long petShopId,
            DayOfWeek dayOfWeek
    );

    // Verificar se já existe horário para aquele dia
    boolean existsByPetShopIdAndDayOfWeek(Long petShopId, DayOfWeek dayOfWeek);

    // Deletar todos os horários de um Pet Shop
    @Modifying
    @Query("DELETE FROM Schedule s WHERE s.petShopId = :petShopId")
    void deleteByPetShopId(@Param("petShopId") Long petShopId);

    // Desativar todos os horários de um Pet Shop
    @Modifying
    @Query("UPDATE Schedule s SET s.isActive = false WHERE s.petShopId = :petShopId")
    void deactivateByPetShopId(@Param("petShopId") Long petShopId);
}