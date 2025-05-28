package com.app.JWTImplementation.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.app.JWTImplementation.dto.ReserveDTO;
import com.app.JWTImplementation.exceptions.*;
import com.app.JWTImplementation.model.*;
import com.app.JWTImplementation.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.JWTImplementation.dto.ReserveInfoDTO;
import com.app.JWTImplementation.dto.projection.ReserveProjection;
import com.app.JWTImplementation.service.impl.IReserveService;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReserveService implements IReserveService {

    @Autowired
    private ReserveRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfessionalRepository professionalRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private ServiceSpaRepository serviceSpaRepository;

    @Override
    public List<Reserve> findAllReserves() {
        return repository.findAll();
    }

    @Transactional
    public Reserve saveReserve(ReserveDTO reserveDetails) {
        // Validación básica
        if (reserveDetails.getScheduleId() == null &&
                (reserveDetails.getServiceId() == null || reserveDetails.getSelectedTime() == null)) {
            throw new InvalidReservationException("Debe proporcionar scheduleId o (serviceId + selectedTime)");
        }

        // Verificar si el usuario existe
        User user = userRepository.findById(reserveDetails.getUserId())
                .orElseThrow(() -> new UserNotFoundException(reserveDetails.getUserId()));

        // verificar que el usuario sea de tipo CUSTOMER
        if (!(user instanceof Customer)) {
            throw new InvalidReservationException("Solo los usuarios con rol de CLIENTE pueden realizar reservas");
        }

        // Verificar si el professional existe
        Professional professional = professionalRepository.findById(reserveDetails.getProfessionalId())
                .orElseThrow(() -> new ProfessionalNotFoundException(reserveDetails.getProfessionalId()));

        Schedule schedule;
        if (reserveDetails.getScheduleId() != null) {
            // Caso A: Horario existente
            schedule = scheduleRepository.findById(reserveDetails.getScheduleId())
                    .orElseThrow(() -> new ScheduleNotFoundException(reserveDetails.getScheduleId()));

            // Validar duplicados para el mismo usuario
            if (repository.existsByUserAndSchedule(user.getId(), schedule.getId())) {
                throw new InvalidReservationException("Ya tienes una reserva para este horario");
            }
        } else {
            // Caso B: Nuevo horario
            ServiceSpa service = serviceSpaRepository.findById(reserveDetails.getServiceId())
                    .orElseThrow(() -> new ServiceSpaNotFoundException(reserveDetails.getServiceId()));

            // Verificar si ya existe un horario para ese servicio en ese tiempo
            List<Schedule> existingSchedules = scheduleRepository.findByServiceAndStartDatetime(
                    service, reserveDetails.getSelectedTime());

            if (!existingSchedules.isEmpty()) {
                schedule = existingSchedules.get(0); // Toma el primero o aplica alguna lógica para elegir

                // IMPORTANTE: Primero verificar si el usuario ya tiene reserva para este horario específico
                // Verificación explícita consultando la tabla de reservas
                List<Reserve> userReservesForSchedule = repository.findByUserIdAndScheduleId(user.getId(), schedule.getId());
                if (!userReservesForSchedule.isEmpty()) {
                    throw new InvalidReservationException("Ya tienes una reserva para este horario");
                }

                // Para servicios no grupales, verificar si ya está reservado
                if (!service.getIsGroupService() && schedule.getCurrentCapacity() > 0) {
                    throw new InvalidReservationException("Este horario ya está reservado");
                }
            } else {
                schedule = Schedule.builder()
                        .service(service)
                        .startDatetime(reserveDetails.getSelectedTime())
                        .endDatetime(reserveDetails.getSelectedTime().plusMinutes(service.getDurationMinutes()))
                        .maxCapacity(service.getIsGroupService() ? 10 : 1)
                        .currentCapacity(0)
                        .isActive(true)
                        .build();

                schedule = scheduleRepository.save(schedule); // guarda el horario
            }
        }

        validateReservation(schedule);

        // Actualizar capacidad
        schedule.setCurrentCapacity(schedule.getCurrentCapacity() + 1);
        schedule = scheduleRepository.save(schedule);

        Reserve reserve = Reserve.builder()
                .dateReserve(LocalDateTime.now())
                .user(user)
                .schedule(schedule)
                .status(Reserve.StatusReserve.CONFIRMED)
                .professional(professional)
                .build();

        return repository.save(reserve);
    }

    // revisar
    @Override
    @Transactional
    public Reserve updateReserve(Integer id, ReserveDTO reserveDetails) {

        // verificar si existe la reserva
        Reserve reserve = this.findReserveById(id);

        // verificar si existe el usuario
        User user = userRepository.findById(reserveDetails.getUserId())
                .orElseThrow(() -> new UserNotFoundException(id));

        // verificar si existe el horario
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new ScheduleNotFoundException(id));

        reserve.setDateReserve(LocalDateTime.now());
        reserve.setUser(user);
        reserve.setSchedule(schedule);

        return reserve;

    }

    @Override
    public Reserve findReserveById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ReserveNotFoundException(id));
    }

    @Override
    public void deleteReserveById(Integer id) {
        Reserve reserve = this.findReserveById(id);
        repository.delete(reserve);
    }

    // metodos sacado desde el repositorio
    public List<ReserveInfoDTO> findAllReservesWhitEntities() {

        List<ReserveProjection> reserveProjections = repository.findAllReservesProjection();

        List<ReserveInfoDTO> reservesDTO = reserveProjections.stream()
                .map(reserve -> {

                    ReserveInfoDTO dto = ReserveInfoDTO.builder()
                            .id(reserve.getId())
                            .dateReserve(reserve.getDateReserve().toLocalDate())
                            .userFullName(reserve.getUserFullName())
                            .serviceName(reserve.getServiceName())
                            .servicePrice(reserve.getServicePrice())
                            .startDate(reserve.getScheduleStart().toLocalDate())
                            .startTime(reserve.getScheduleStart().toLocalTime())
                            .endTime(reserve.getScheduleEnd().toLocalTime())
                            .professionalName(reserve.getProfessionalName())
                            .status(reserve.getStatus())
                            .build();

                    return dto;

                }).collect(Collectors.toList());

        return reservesDTO;

    }

    public ReserveInfoDTO findReserveWithEntityById(Integer id) {

        ReserveProjection reserve = repository.findReserveProjectionById(id)
                .orElseThrow(() -> new ReserveNotFoundException(id));

        ReserveInfoDTO reserveDTO = ReserveInfoDTO.builder()
                .id(reserve.getId())
                .dateReserve(reserve.getDateReserve().toLocalDate())
                .userFullName(reserve.getUserFullName())
                .serviceName(reserve.getServiceName())
                .servicePrice(reserve.getServicePrice())
                .startDate(reserve.getScheduleStart().toLocalDate())
                .startTime(reserve.getScheduleStart().toLocalTime())
                .endTime(reserve.getScheduleEnd().toLocalTime())
                .professionalName(reserve.getProfessionalName())
                .status(reserve.getStatus())
                .build();

        return reserveDTO;

    }

    private void validateReservation(Schedule schedule) {

        if (schedule.getStartDatetime().isBefore(LocalDateTime.now().plusDays(3))) {
            throw new InvalidReservationException("Se requiere al menos 3 dias de anticipacion");
        }

        // capacidad actual >= capacidad maxima
        if (schedule.getCurrentCapacity() >= schedule.getMaxCapacity()) {
            throw new InvalidReservationException("No hay cupos disponibles");
        }

        if (!schedule.getIsActive()) {
            throw new InvalidReservationException("El horario no esta disponible");
        }

        // Verificar si el horario ya esta ocupado
        if (!schedule.getService().getIsGroupService() && schedule.getCurrentCapacity() > 0) {
            throw new InvalidReservationException("Este horario ya esta reservado");
        }

    }

    private Schedule generateSingleSchedule(ReserveDTO reserveDetails) {

        ServiceSpa service = serviceSpaRepository.findById(reserveDetails.getServiceId())
                .orElseThrow(() -> new ReserveNotFoundException(reserveDetails.getServiceId()));

        return Schedule.builder()
                .startDatetime(reserveDetails.getSelectedTime())
                .endDatetime(reserveDetails.getSelectedTime().plusMinutes(service.getDurationMinutes()))
                .service(service)
                .currentCapacity(0)
                .maxCapacity(service.getIsGroupService() ? 10 : 1)
                .isActive(true)
                .build();

    }

}
