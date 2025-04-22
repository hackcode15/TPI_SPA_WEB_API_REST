/*
package com.app.JWTImplementation;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.app.JWTImplementation.dto.generationSchedulesDTOs.DailyScheduleDTO;
import com.app.JWTImplementation.dto.generationSchedulesDTOs.ServiceScheduleDTO;
import com.app.JWTImplementation.dto.generationSchedulesDTOs.TimeSlotAvailabilityDTO;
import com.app.JWTImplementation.exceptions.ScheduleNotFoundException;
import com.app.JWTImplementation.exceptions.ServiceSpaNotFoundException;
import com.app.JWTImplementation.exceptions.UserNotFoundException;
import com.app.JWTImplementation.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.app.JWTImplementation.model.User.Role;

import com.app.JWTImplementation.repository.ReserveRepository;
import com.app.JWTImplementation.repository.ScheduleRepository;
import com.app.JWTImplementation.repository.ServiceSpaRepository;
import com.app.JWTImplementation.repository.UserRepository;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UserRepository repoUser;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ServiceSpaRepository repoServiceSpa;
    @Autowired
    private ScheduleRepository repoSchedule;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ReserveRepository repoReserve;

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        // GENERATION USERS
        //generateUsers();

        // GENERATION MAIN CATEGORY, SUBCATEGORY AND SERVICES SPA
        //generatesServicesSpa();

        // GENERATION SCHEDULES
        //generateTestSchedulesForDemo();

    }

    private void generateUsers() {

        // ADMIN
        User diego = User.builder()
                .username("diego")
                .password(passwordEncoder.encode("2004"))
                .firstName("Diego Elias")
                .lastName("Gomez")
                .role(Role.CUSTOMER)
                .build();

        // USER
        User vale = User.builder()
                .username("vale")
                .password(passwordEncoder.encode("2016"))
                .firstName("Valentina Lara")
                .lastName("Gomez")
                .role(Role.CUSTOMER)
                .build();

        // USER
        User lidia = User.builder()
                .username("lidia")
                .password(passwordEncoder.encode("1984"))
                .firstName("Lidia Celeste")
                .lastName("Salinas")
                .role(Role.CUSTOMER)
                .build();

        repoUser.saveAll(List.of(diego, vale, lidia));

    }

    private void generatesServicesSpa() {

        // ------------------------------------------------------------------------------------
        // SERVICIOS
        // INDIVIDUALES
        // MASAJES
        ServiceSpa antiStress = ServiceSpa.builder()
                .name("Anti-stress")
                .description(null)
                .categoryName("MASAJE")
                .durationMinutes(60)
                .isActive(true)
                .isGroupService(false)
                .build();

        ServiceSpa descontracturantes = ServiceSpa.builder()
                .name("Descontracturantes")
                .description(null)
                .categoryName("MASAJE")
                .durationMinutes(60)
                .isActive(true)
                .isGroupService(false)
                .build();

        // BELLEZA
        ServiceSpa liftingDePestana = ServiceSpa.builder()
                .name("Lifting de pestaña")
                .description(null)
                .categoryName("BELLEZA")
                .durationMinutes(60)
                .isActive(true)
                .isGroupService(false)
                .build();

        ServiceSpa depelicacionFacial = ServiceSpa.builder()
                .name("Depilación facial")
                .description(null)
                .categoryName("BELLEZA")
                .durationMinutes(60)
                .isActive(true)
                .isGroupService(false)
                .build();

        // TRATAMIENTOS FACIALES
        ServiceSpa puntaDeDiamante = ServiceSpa.builder()
                .name("Punta de diamante")
                .description(null)
                .categoryName("TRATAMIENTOS FACIALES")
                .durationMinutes(60)
                .isActive(true)
                .isGroupService(false)
                .build();

        ServiceSpa crioFrecuenciaFacial = ServiceSpa.builder()
                .name("Crio frecuencia facial")
                .description(null)
                .categoryName("TRATAMIENTOS FACIALES")
                .durationMinutes(60)
                .isActive(true)
                .isGroupService(false)
                .build();

        // TRATAMIENTOS CORPORALES
        ServiceSpa velaSlim = ServiceSpa.builder()
                .name("Vela slim")
                .description(null)
                .categoryName("TRATAMIENTOS CORPORALES")
                .durationMinutes(60)
                .isActive(true)
                .isGroupService(false)
                .build();

        ServiceSpa dermoHealth = ServiceSpa.builder()
                .name("dermo-health")
                .description(null)
                .categoryName("TRATAMIENTOS CORPORALES")
                .durationMinutes(60)
                .isActive(true)
                .isGroupService(false)
                .build();

        // GRUPAL
        ServiceSpa hidromasajes = ServiceSpa.builder()
                .name("Hidromasajes")
                .description(null)
                .categoryName("GRUPALES")
                .durationMinutes(60)
                .isActive(true)
                .isGroupService(true)
                .build();

        ServiceSpa yoga = ServiceSpa.builder()
                .name("Yoga")
                .description(null)
                .categoryName("GRUPALES")
                .durationMinutes(60)
                .isActive(true)
                .isGroupService(true)
                .build();

        repoServiceSpa.saveAll(
                List.of(
                        antiStress,
                        descontracturantes,
                        liftingDePestana,
                        depelicacionFacial,
                        puntaDeDiamante,
                        crioFrecuenciaFacial,
                        velaSlim,
                        dermoHealth,
                        hidromasajes,
                        yoga
                )
        );
        // ------------------------------------------------------------------------------------

    }

    private void generateTestSchedulesForDemo() {

        ServiceSpa service = repoServiceSpa.findByName("Anti-stress")
                .orElseThrow(() -> new ServiceSpaNotFoundException("Service not found whit name: Anti-Stress"));

        // Horario de ejemplo para hoy + 3 dias (cumple validacion)
        LocalDateTime start = LocalDateTime.now().plusDays(3).withHour(14).withMinute(0);

        Schedule testSchedule = Schedule.builder()
                .service(service)
                .startDatetime(start)
                .endDatetime(start.plusMinutes(service.getDurationMinutes()))
                .maxCapacity(1)
                .currentCapacity(0)
                .isActive(true)
                .build();

        repoSchedule.save(testSchedule);

    }

}
*/
