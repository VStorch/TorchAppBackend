package com.torchapp.demo.services;


import com.torchapp.demo.dtos.schedule.ScheduleBulkDTO;
import com.torchapp.demo.dtos.schedule.ScheduleBulkResponseDTO;
import com.torchapp.demo.dtos.schedule.ScheduleDTO;
import com.torchapp.demo.dtos.schedule.ScheduleResponseDTO;
import com.torchapp.demo.enums.DayOfWeek;
import com.torchapp.demo.exceptions.ResourceNotFoundException;
import com.torchapp.demo.models.Schedule;
import com.torchapp.demo.repositories.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    // Mapeamento dos dias em português para o enum
    private static final Map<String, DayOfWeek> DAY_MAP = new HashMap<>();
    static {
        DAY_MAP.put("Ter", DayOfWeek.TER);
        DAY_MAP.put("Qua", DayOfWeek.QUA);
        DAY_MAP.put("Seg", DayOfWeek.SEG);
        DAY_MAP.put("Qui", DayOfWeek.QUI);
        DAY_MAP.put("Sex", DayOfWeek.SEX);
        DAY_MAP.put("Sáb", DayOfWeek.SAB);
        DAY_MAP.put("Dom", DayOfWeek.DOM);
    }

     // Salvar ou atualizar horários em lote (como vem do Flutter)
    @Transactional
    public ScheduleBulkResponseDTO saveBulkSchedules(ScheduleBulkDTO bulkDTO) {
        try {
            // Deletar horários antigos do Pet Shop
            scheduleRepository.deleteByPetShopId(bulkDTO.getPetShopId());

            List<Schedule> savedSchedules = new ArrayList<>();

            for (ScheduleBulkDTO.DaySchedule daySchedule : bulkDTO.getSchedules()) {
                // Pular se os horários estiverem vazios
                if (daySchedule.getOpenTime() == null || daySchedule.getOpenTime().isEmpty() ||
                        daySchedule.getCloseTime() == null || daySchedule.getCloseTime().isEmpty()) {
                    continue;
                }

                DayOfWeek dayOfWeek = DAY_MAP.get(daySchedule.getDay());
                if (dayOfWeek == null) {
                    continue; // Dia não reconhecido
                }

                Schedule schedule = new Schedule();
                schedule.setPetShopId(bulkDTO.getPetShopId());
                schedule.setDayOfWeek(dayOfWeek);
                schedule.setOpenTime(parseTime(daySchedule.getOpenTime()));
                schedule.setCloseTime(parseTime(daySchedule.getCloseTime()));
                schedule.setIsActive(true);

                savedSchedules.add(scheduleRepository.save(schedule));
            }

            List<ScheduleResponseDTO> responseDTOs = savedSchedules.stream()
                    .map(ScheduleResponseDTO::fromEntity)
                    .collect(Collectors.toList());

            return new ScheduleBulkResponseDTO(
                    true,
                    "Horários salvos com sucesso!",
                    responseDTOs
            );

        } catch (Exception e) {
            return new ScheduleBulkResponseDTO(
                    false,
                    "Erro ao salvar horários: " + e.getMessage(),
                    null
            );
        }
    }

     // Criar um horário individual
    @Transactional
    public ScheduleResponseDTO createSchedule(ScheduleDTO dto) {
        // Verificar se já existe horário para aquele dia
        if (scheduleRepository.existsByPetShopIdAndDayOfWeek(dto.getPetShopId(), dto.getDayOfWeek())) {
            throw new IllegalArgumentException("Já existe horário cadastrado para este dia");
        }

        Schedule schedule = new Schedule();
        schedule.setPetShopId(dto.getPetShopId());
        schedule.setDayOfWeek(dto.getDayOfWeek());
        schedule.setOpenTime(dto.getOpenTimeAsLocalTime());
        schedule.setCloseTime(dto.getCloseTimeAsLocalTime());
        schedule.setIsActive(true);

        Schedule saved = scheduleRepository.save(schedule);
        return ScheduleResponseDTO.fromEntity(saved);
    }

     // Atualizar um horário existente
    @Transactional
    public ScheduleResponseDTO updateSchedule(Long id, ScheduleDTO dto) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Horário não encontrado"));

        schedule.setOpenTime(dto.getOpenTimeAsLocalTime());
        schedule.setCloseTime(dto.getCloseTimeAsLocalTime());
        if (dto.getIsActive() != null) {
            schedule.setIsActive(dto.getIsActive());
        }

        Schedule updated = scheduleRepository.save(schedule);
        return ScheduleResponseDTO.fromEntity(updated);
    }

    // Buscar todos os horários de um Pet Shop
    public List<ScheduleResponseDTO> getSchedulesByPetShopId(Long petShopId) {
        return scheduleRepository.findByPetShopIdAndIsActiveTrue(petShopId)
                .stream()
                .map(ScheduleResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

     // Buscar horário de um dia específico
    public ScheduleResponseDTO getScheduleByDay(Long petShopId, DayOfWeek dayOfWeek) {
        Schedule schedule = scheduleRepository
                .findByPetShopIdAndDayOfWeekAndIsActiveTrue(petShopId, dayOfWeek)
                .orElseThrow(() -> new ResourceNotFoundException("Horário não encontrado para este dia"));

        return ScheduleResponseDTO.fromEntity(schedule);
    }


     // Deletar um horário específico
    @Transactional
    public void deleteSchedule(Long id) {
        if (!scheduleRepository.existsById(id)) {
            throw new ResourceNotFoundException("Horário não encontrado");
        }
        scheduleRepository.deleteById(id);
    }


     // Deletar todos os horários de um Pet Shop
    @Transactional
    public void deleteAllSchedulesByPetShop(Long petShopId) {
        scheduleRepository.deleteByPetShopId(petShopId);
    }

    /**
     * Converter string de hora para LocalTime
     * Aceita formatos: "HH:mm", "HH:mm AM/PM", "h:mm AM"
     */
    private LocalTime parseTime(String timeString) {
        if (timeString == null || timeString.isEmpty()) {
            throw new IllegalArgumentException("Horário inválido");
        }

        try {
            // Tentar formato 24h primeiro
            return LocalTime.parse(timeString, DateTimeFormatter.ofPattern("HH:mm"));
        } catch (DateTimeParseException e1) {
            try {
                // Tentar formato 12h com AM/PM
                return LocalTime.parse(timeString, DateTimeFormatter.ofPattern("h:mm a"));
            } catch (DateTimeParseException e2) {
                try {
                    return LocalTime.parse(timeString, DateTimeFormatter.ofPattern("hh:mm a"));
                } catch (DateTimeParseException e3) {
                    throw new IllegalArgumentException("Formato de hora inválido: " + timeString);
                }
            }
        }
    }
}