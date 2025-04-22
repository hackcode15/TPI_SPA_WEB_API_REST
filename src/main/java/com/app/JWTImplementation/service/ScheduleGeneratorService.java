package com.app.JWTImplementation.service;

import com.app.JWTImplementation.dto.ScheduleInfoDTO;
import com.app.JWTImplementation.dto.projection.ScheduleProjection;
import com.app.JWTImplementation.exceptions.ServiceSpaNotFoundException;
import com.app.JWTImplementation.model.Schedule;
import com.app.JWTImplementation.model.ServiceSpa;
import com.app.JWTImplementation.repository.ScheduleRepository;
import com.app.JWTImplementation.repository.ServiceSpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


/*@Service
public class ScheduleGeneratorService {

    @Autowired
    private ServiceSpaRepository serviceSpaRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Transactional(readOnly = true)
    public List<ScheduleInfoDTO> generateAvailableSchedules(LocalDate date, Integer serviceId, int daysToShow) {
        // Obtener el servicio COMPLETO con todos los campos necesarios
        ServiceSpa service = serviceSpaRepository.findById(serviceId)
                .orElseThrow(() -> new ServiceSpaNotFoundException(serviceId));

        // Validar que durationMinutes no sea null
        if(service.getDurationMinutes() == null) {
            throw new IllegalStateException("El servicio con ID " + serviceId + " no tiene duración configurada");
        }

        List<ScheduleInfoDTO> availableSchedules = new ArrayList<>();
        LocalDate endDate = date.plusDays(daysToShow);

        for (LocalDate currentDate = date; currentDate.isBefore(endDate); currentDate = currentDate.plusDays(1)) {
            List<Schedule> existingSchedules = scheduleRepository.findByServiceAndDate(service, currentDate);
            List<Schedule> generatedSchedules = generateDefaultSchedules(service, currentDate);
            List<ScheduleInfoDTO> merged = mergeSchedules(generatedSchedules, existingSchedules);
            availableSchedules.addAll(merged);
        }

        return availableSchedules;
    }

    private List<Schedule> generateDefaultSchedules(ServiceSpa service, LocalDate date) {

        List<Schedule> schedules = new ArrayList<>();

        LocalTime[] timeSlots = {
                LocalTime.of(9, 0), // fin 60min - 10
                LocalTime.of(11, 0),
                LocalTime.of(14, 0),
                LocalTime.of(16, 0),
                LocalTime.of(18, 0),
        };

        for (LocalTime startTime : timeSlots) {

            LocalDateTime startDateTime = LocalDateTime.of(date, startTime);
            LocalDateTime endDateTime = startDateTime.plusMinutes(service.getDurationMinutes());

            Schedule schedule = Schedule.builder()
                    .service(service)
                    .startDatetime(startDateTime)
                    .endDatetime(endDateTime)
                    .currentCapacity(0)
                    .maxCapacity(service.getIsGroupService() ? 10 : 1)
                    .isActive(true)
                    .build();

            schedules.add(schedule);

        }

        return schedules;

    }

    private ScheduleInfoDTO convertToDTO(Schedule schedule) {
        return ScheduleInfoDTO.builder()
                .id(schedule.getId())
                .startDate(schedule.getStartDatetime().toLocalDate())
                .startTime(schedule.getStartDatetime().toLocalTime())
                .endDate(schedule.getEndDatetime().toLocalDate())
                .endTime(schedule.getEndDatetime().toLocalTime())
                .maxCapacity(schedule.getMaxCapacity())
                .currentCapacity(schedule.getCurrentCapacity())
                .isActive(schedule.getIsActive())
                .service(schedule.getService().getName())
                .available(schedule.getIsActive() &&
                        schedule.getCurrentCapacity() < schedule.getMaxCapacity())
                .build();
    }

    private List<ScheduleInfoDTO> mergeSchedules(List<Schedule> generated, List<Schedule> persisted) {

        Map<LocalTime, Schedule> persistedSlots = persisted.stream()
                .collect(Collectors.toMap(
                        slot -> slot.getStartDatetime().toLocalTime(),
                        Function.identity()
                ));

        return generated.stream()
                .map(generatedSlot -> {
                    LocalTime slotTime = generatedSlot.getStartDatetime().toLocalTime();
                    Schedule persistedSlot = persistedSlots.get(slotTime);
                    return convertToDTO(persistedSlot != null ? persistedSlot : generatedSlot);
                })
                .collect(Collectors.toList());

    }

}*/

@Service
@Transactional(readOnly = true)
public class ScheduleGeneratorService {

    @Autowired
    private ServiceSpaRepository serviceSpaRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    public List<ScheduleInfoDTO> generateAvailableSchedules(LocalDate date, Integer serviceId, int daysToShow) {
        // 1. Obtener solo la información necesaria del servicio
        String serviceName = serviceSpaRepository.findServiceNameById(serviceId)
                .orElseThrow(() -> new ServiceSpaNotFoundException(serviceId));

        Integer durationMinutes = serviceSpaRepository.findDurationMinutesById(serviceId)
                .orElseThrow(() -> new IllegalStateException("El servicio no tiene duración configurada"));

        // 2. Crear un objeto ServiceSpa ligero solo con la información necesaria
        ServiceSpa service = new ServiceSpa();
        service.setId(serviceId);
        service.setName(serviceName);
        service.setDurationMinutes(durationMinutes);
        service.setIsGroupService(serviceSpaRepository.findIsGroupServiceById(serviceId));

        List<ScheduleInfoDTO> availableSchedules = new ArrayList<>();
        LocalDate endDate = date.plusDays(daysToShow);

        for (LocalDate currentDate = date; currentDate.isBefore(endDate); currentDate = currentDate.plusDays(1)) {
            // 3. Usar proyecciones para evitar cargar entidades completas
            List<ScheduleProjection> existingSchedules = scheduleRepository.findAllSchedulesOfServiceByDate(currentDate, serviceId);

            // 4. Convertir proyecciones a DTOs
            List<ScheduleInfoDTO> existingDTOs = existingSchedules.stream()
                    .map(this::convertProjectionToDTO)
                    .collect(Collectors.toList());

            // 5. Generar horarios predeterminados
            List<ScheduleInfoDTO> generatedDTOs = generateDefaultSchedulesAsDTOs(service, currentDate);

            // 6. Combinar ambos
            List<ScheduleInfoDTO> merged = mergeSchedules(generatedDTOs, existingDTOs);
            availableSchedules.addAll(merged);
        }

        return availableSchedules;
    }

    private ScheduleInfoDTO convertProjectionToDTO(ScheduleProjection projection) {
        return ScheduleInfoDTO.builder()
                .id(projection.getId())
                .startDate(projection.getStartDatetime().toLocalDate())
                .startTime(projection.getStartDatetime().toLocalTime())
                .endDate(projection.getEndDatetime().toLocalDate())
                .endTime(projection.getEndDatetime().toLocalTime())
                .maxCapacity(projection.getMaxCapacity())
                .currentCapacity(projection.getCurrentCapacity())
                .isActive(projection.getIsActive())
                .service(projection.getServiceName())
                .build();
    }

    private List<ScheduleInfoDTO> generateDefaultSchedulesAsDTOs(ServiceSpa service, LocalDate date) {
        List<ScheduleInfoDTO> schedules = new ArrayList<>();

        LocalTime[] timeSlots = {
                LocalTime.of(9, 0),
                LocalTime.of(11, 0),
                LocalTime.of(14, 0),
                LocalTime.of(16, 0),
                LocalTime.of(18, 0),
        };

        for (LocalTime startTime : timeSlots) {
            LocalDateTime startDateTime = LocalDateTime.of(date, startTime);
            LocalDateTime endDateTime = startDateTime.plusMinutes(service.getDurationMinutes());

            ScheduleInfoDTO dto = ScheduleInfoDTO.builder()
                    .startDate(date)
                    .startTime(startTime)
                    .endDate(date)
                    .endTime(endDateTime.toLocalTime())
                    .maxCapacity(service.getIsGroupService() ? 10 : 1)
                    .currentCapacity(0)
                    .isActive(true)
                    .service(service.getName())
                    .build();

            schedules.add(dto);
        }

        return schedules;
    }

    private List<ScheduleInfoDTO> mergeSchedules(List<ScheduleInfoDTO> generated, List<ScheduleInfoDTO> persisted) {
        // Cambia la clave para incluir tanto la hora de inicio como la de fin
        Map<String, ScheduleInfoDTO> persistedSlots = persisted.stream()
                .collect(Collectors.toMap(
                        slot -> slot.getStartTime() + "-" + slot.getEndTime(), // Clave única
                        Function.identity(),
                        (existing, replacement) -> existing // Maneja duplicados conservando el existente
                ));

        return generated.stream()
                .map(generatedSlot -> {
                    String slotKey = generatedSlot.getStartTime() + "-" + generatedSlot.getEndTime();
                    ScheduleInfoDTO persistedSlot = persistedSlots.get(slotKey);
                    return persistedSlot != null ? persistedSlot : generatedSlot;
                })
                .collect(Collectors.toList());
    }

}
