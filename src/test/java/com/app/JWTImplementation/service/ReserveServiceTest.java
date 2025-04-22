/*
package com.app.JWTImplementation.service;

import com.app.JWTImplementation.dto.ReserveDTO;
import com.app.JWTImplementation.exceptions.InvalidReservationException;
import com.app.JWTImplementation.exceptions.ScheduleNotFoundException;
import com.app.JWTImplementation.exceptions.UserNotFoundException;
import com.app.JWTImplementation.model.Reserve;
import com.app.JWTImplementation.model.Schedule;
import com.app.JWTImplementation.model.ServiceSpa;
import com.app.JWTImplementation.model.User;
import com.app.JWTImplementation.repository.ReserveRepository;
import com.app.JWTImplementation.repository.ScheduleRepository;
import com.app.JWTImplementation.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReserveServiceTest {

    @Mock
    private ReserveRepository reserveRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ScheduleRepository scheduleRepository;

    @InjectMocks
    private ReserveService reserveService;

    private User user;
    private ServiceSpa serviceIndividual;
    private Schedule scheduleServiceIndividual;
    private Schedule scheduleServiceGroup;

    @BeforeEach
    public void init() {
        user = User.builder()
                .id(1)
                .username("diego")
                .password("1234")
                .firstName("Diego Elias")
                .lastName("Gomez")
                .build();

        serviceIndividual = ServiceSpa.builder()
                .id(1)
                .name("Anti-stress")
                .description(null)
                .categoryName("MASAJE")
                .durationMinutes(60)
                .isActive(true)
                .isGroupService(false)
                .build();

        serviceIndividual.setSchedules(List.of(
                Schedule.builder()
                        .id(1)
                        .startDatetime(LocalDateTime.now().plusDays(3).withHour(18).withMinute(0))
                        .endDatetime(LocalDateTime.now().plusDays(3).withHour(19).withMinute(0))
                        .currentCapacity(0)
                        .maxCapacity(1)
                        .isActive(true)
                        .service(serviceIndividual)
                        .build()
        ));

        scheduleServiceIndividual = serviceIndividual.getSchedules().get(0);

        // Configuración para servicio grupal
        ServiceSpa serviceGroup = ServiceSpa.builder()
                .id(2)
                .name("Yoga Grupal")
                .description("Clase de yoga para grupos")
                .categoryName("YOGA")
                .durationMinutes(90)
                .isActive(true)
                .isGroupService(true)
                .build();

        scheduleServiceGroup = Schedule.builder()
                .id(2)
                .startDatetime(LocalDateTime.now().plusDays(4).withHour(10).withMinute(0))
                .endDatetime(LocalDateTime.now().plusDays(4).withHour(11).withMinute(30))
                .currentCapacity(5)
                .maxCapacity(10)
                .isActive(true)
                .service(serviceGroup)
                .build();
    }

    @Test
    public void reserveService_whenCreateReservationOfServiceIndividualWithLessThan3Days_ThenThrowException() {
        // Configurar un horario con menos de 3 días de anticipación
        LocalDateTime invalidDateTime = LocalDateTime.now().plusDays(2).withHour(18).withMinute(0);

        Schedule invalidSchedule = Schedule.builder()
                .id(3)
                .startDatetime(invalidDateTime)
                .endDatetime(invalidDateTime.plusHours(1))
                .currentCapacity(0)
                .maxCapacity(1)
                .isActive(true)
                .service(serviceIndividual)
                .build();

        // Configurar el DTO de reserva
        ReserveDTO reserveDTO = ReserveDTO.builder()
                .userId(1)
                .scheduleId(invalidSchedule.getId())
                .build();

        // Mockear las dependencias
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(scheduleRepository.findById(3)).thenReturn(Optional.of(invalidSchedule));

        // Verificar que se lanza la excepción
        assertThrows(InvalidReservationException.class, () -> {
            reserveService.saveReserve(reserveDTO);
        });

        // Verificar que no se guardó la reserva
        verify(reserveRepository, never()).save(any());

    }

    @Test
    public void reserveService_whenCreateReservationOfServiceIndividualWithValidDate_ThenReturnReserve() {
        // Configurar un horario válido (3 días o más en el futuro)
        LocalDateTime validDateTime = LocalDateTime.now().plusDays(4).withHour(8).withMinute(0);

        Schedule validSchedule = Schedule.builder()
                .id(1)
                .startDatetime(validDateTime)
                .endDatetime(validDateTime.plusHours(1))
                .currentCapacity(0)
                .maxCapacity(1)
                .isActive(true)
                .service(serviceIndividual)
                .build();

        // Configurar el DTO de reserva
        ReserveDTO reserveDTO = ReserveDTO.builder()
                .userId(1)
                .scheduleId(validSchedule.getId())
                .build();

        // Mockear las dependencias
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(scheduleRepository.findById(1)).thenReturn(Optional.of(validSchedule));
        when(reserveRepository.save(any(Reserve.class))).thenAnswer(invocation -> {
            Reserve reserve = invocation.getArgument(0);
            reserve.setId(1);
            return reserve;
        });

        // Ejecutar el método
        Reserve result = reserveService.saveReserve(reserveDTO);

        // Verificaciones
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1);
        assertThat(result.getUser()).isEqualTo(user);
        assertThat(result.getSchedule()).isEqualTo(validSchedule);
        assertThat(result.getStatus()).isEqualTo(Reserve.StatusReserve.CONFIRMED);

        // Verificar que se incrementó la capacidad actual
        assertThat(validSchedule.getCurrentCapacity()).isEqualTo(1);

        // Verificar interacciones con los repositorios
        verify(reserveRepository, times(1)).save(any(Reserve.class));
        verify(scheduleRepository, times(1)).save(validSchedule);
    }

    @Test
    public void reserveService_whenScheduleIsFull_ThenThrowException() {
        // Configurar un horario lleno
        Schedule fullSchedule = Schedule.builder()
                .id(1)
                .startDatetime(LocalDateTime.now().plusDays(3).withHour(18).withMinute(0))
                .endDatetime(LocalDateTime.now().plusDays(3).withHour(19).withMinute(0))
                .currentCapacity(1) // Máxima capacidad para servicio individual
                .maxCapacity(1)
                .isActive(true)
                .service(serviceIndividual)
                .build();

        // Configurar el DTO de reserva
        ReserveDTO reserveDTO = ReserveDTO.builder()
                .userId(1)
                .scheduleId(fullSchedule.getId())
                .build();

        // Mockear las dependencias
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(scheduleRepository.findById(1)).thenReturn(Optional.of(fullSchedule));

        // Verificar que se lanza la excepción
        assertThrows(InvalidReservationException.class, () -> {
            reserveService.saveReserve(reserveDTO);
        });

        // Verificar que no se guardó la reserva
        verify(reserveRepository, never()).save(any());
    }

    @Test
    public void reserveService_whenScheduleIsInactive_ThenThrowException() {
        // Configurar un horario inactivo
        Schedule inactiveSchedule = Schedule.builder()
                .id(1)
                .startDatetime(LocalDateTime.now().plusDays(3).withHour(18).withMinute(0))
                .endDatetime(LocalDateTime.now().plusDays(3).withHour(19).withMinute(0))
                .currentCapacity(0)
                .maxCapacity(1)
                .isActive(false) // Horario inactivo
                .service(serviceIndividual)
                .build();

        // Configurar el DTO de reserva
        ReserveDTO reserveDTO = ReserveDTO.builder()
                .userId(1)
                .scheduleId(inactiveSchedule.getId())
                .build();

        // Mockear las dependencias
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(scheduleRepository.findById(1)).thenReturn(Optional.of(inactiveSchedule));

        // Verificar que se lanza la excepción
        assertThrows(InvalidReservationException.class, () -> {
            reserveService.saveReserve(reserveDTO);
        });

        // Verificar que no se guardó la reserva
        verify(reserveRepository, never()).save(any());
    }

    @Test
    public void reserveService_whenCreateGroupReservationWithAvailableSpace_ThenReturnReserve() {
        // Configurar el DTO de reserva
        ReserveDTO reserveDTO = ReserveDTO.builder()
                .userId(1)
                .scheduleId(scheduleServiceGroup.getId())
                .build();

        // Mockear las dependencias
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(scheduleRepository.findById(2)).thenReturn(Optional.of(scheduleServiceGroup));
        when(reserveRepository.save(any(Reserve.class))).thenAnswer(invocation -> {
            Reserve reserve = invocation.getArgument(0);
            reserve.setId(2);
            return reserve;
        });

        // Ejecutar el método
        Reserve result = reserveService.saveReserve(reserveDTO);

        // Verificaciones
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(2);
        assertThat(result.getUser()).isEqualTo(user);
        assertThat(result.getSchedule()).isEqualTo(scheduleServiceGroup);
        assertThat(result.getStatus()).isEqualTo(Reserve.StatusReserve.CONFIRMED);

        // Verificar que se incrementó la capacidad actual
        assertThat(scheduleServiceGroup.getCurrentCapacity()).isEqualTo(6);

        // Verificar interacciones con los repositorios
        verify(reserveRepository, times(1)).save(any(Reserve.class));
        verify(scheduleRepository, times(1)).save(scheduleServiceGroup);
    }

    @Test
    public void reserveService_whenUserNotFound_ThenThrowUserNotFoundException() {
        // Configurar el DTO de reserva
        ReserveDTO reserveDTO = ReserveDTO.builder()
                .userId(99) // ID no existente
                .scheduleId(1)
                .build();

        // Mockear las dependencias
        when(userRepository.findById(99)).thenReturn(Optional.empty());

        // Verificar que se lanza la excepción
        assertThrows(UserNotFoundException.class, () -> {
            reserveService.saveReserve(reserveDTO);
        });

        // Verificar que no se guardó la reserva
        verify(reserveRepository, never()).save(any());

    }

    @Test
    public void reserveService_whenScheduleNotFound_ThenThrowScheduleNotFoundException() {
        // Configurar el DTO de reserva
        ReserveDTO reserveDTO = ReserveDTO.builder()
                .userId(1)
                .scheduleId(99) // ID no existente
                .build();

        // Mockear las dependencias
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(scheduleRepository.findById(99)).thenReturn(Optional.empty());

        // Verificar que se lanza la excepción
        assertThrows(ScheduleNotFoundException.class, () -> {
            reserveService.saveReserve(reserveDTO);
        });

        // Verificar que no se guardó la reserva
        verify(reserveRepository, never()).save(any());
    }
}*/
