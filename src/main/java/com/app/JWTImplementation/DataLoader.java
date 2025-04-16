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
        //generateSchedulesFromJson();

        // GENERATION RESERVE
        //generateReserve();

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

    // Genera bien los horarios ingresados en el JSON - comprobado en postman
    // Pero se ven afectados en la BD
    // por ejemplo si los primeros son 8 a 9, 9 a 10 y 10 a 11, se los salta y empieza desde el cuarto osea desde el 11 a 12
    // al final agrega tres horarios mas para ocupar esos espacios
    // por ejemplo si el horario final en el json es de 15 a 16
    // agrega tres mas de 16 a 17, 17 a 18 y 18 a 19
    private void generateSchedulesFromJson() {
        try {
            // Lectura del JSON
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("jsonSchedules/Schedules1.json");

            if (inputStream == null) {
                System.err.println("JSON no encontrado");
                return;
            }

            // Deserializar JSON a DailyScheduleDTO
            DailyScheduleDTO dailySchedule = objectMapper.readValue(inputStream, DailyScheduleDTO.class);

            // Parsear la fecha del día
            LocalDate scheduleDate = LocalDate.parse(dailySchedule.getDay());
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

            List<Schedule> schedulesToSave = new ArrayList<>();

            // Para cada servicio en el JSON
            for (ServiceScheduleDTO serviceSchedule : dailySchedule.getServices()) {
                // Busca el servicio por nombre y categoría (usando categoryName de ServiceSpa)
                List<ServiceSpa> matchingServices = repoServiceSpa.findByNameAndCategoryName(
                        serviceSchedule.getServiceName(),
                        serviceSchedule.getCategory()
                );

                if (matchingServices.isEmpty()) {
                    System.err.println("Servicio no encontrado: " + serviceSchedule.getServiceName() +
                            " en categoría " + serviceSchedule.getCategory());
                    continue;
                }

                ServiceSpa service = matchingServices.get(0);

                // Para cada slot definido en el JSON
                for (TimeSlotAvailabilityDTO slot : serviceSchedule.getSlots()) {
                    if (!slot.isAvailable()) {
                        continue; // Saltar slots no disponibles
                    }

                    // Parsear la hora de inicio
                    LocalTime startTime = LocalTime.parse(slot.getStartTime(), timeFormatter);

                    // Usar la duración del servicio de la entidad ServiceSpa
                    LocalTime endTime = startTime.plusMinutes(service.getDurationMinutes());

                    // Crear el horario con fecha y hora
                    LocalDateTime startDateTime = LocalDateTime.of(scheduleDate, startTime);
                    LocalDateTime endDateTime = LocalDateTime.of(scheduleDate, endTime);

                    // Usar isGroupService de ServiceSpa para determinar la capacidad
                    int maxCapacity = service.getIsGroupService() ? 10 : 1;

                    // Creación y adición del horario
                    Schedule schedule = Schedule.builder()
                            .service(service)
                            .startDatetime(startDateTime)
                            .endDatetime(endDateTime)
                            .currentCapacity(0)
                            .maxCapacity(maxCapacity)
                            .isActive(true)
                            .build();

                    schedulesToSave.add(schedule);
                }
            }

            // Guardar todos los horarios en la base de datos
            repoSchedule.saveAll(schedulesToSave);
            System.out.println("Horarios generados correctamente");
            System.out.println("Se generaron " + schedulesToSave.size() + " horarios para el día " + dailySchedule.getDay());

        } catch (Exception e) {
            System.err.println("Error al leer el archivo JSON de horarios: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void generateReserve() {
        // ---------------------------------------------------------------------------
        // reserva de Diego de un servicio individual - Anti-stress
        User diego = repoUser.findUserByUsername("diego")
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado con username diego"));

        // Buscar horario para el servicio Anti-stress el 2025-04-20 a las 08:00
        // Mejor buscar por servicio y fecha/hora en lugar de por ID fijo
        LocalDateTime startTime = LocalDateTime.of(2025, 4, 20, 8, 0);
        Schedule schedule = repoSchedule.findByServiceNameAndStartDatetime("Anti-stress", startTime)
                .orElseThrow(() -> new ScheduleNotFoundException("Schedule no encontrado"));

        if(schedule.getCurrentCapacity() >= schedule.getMaxCapacity()) {
            throw new RuntimeException("No hay espacio disponible para la reserva");
        }

        Reserve reserve = Reserve.builder()
                .dateReserve(LocalDateTime.now())
                .status(Reserve.StatusReserve.CONFIRMED)
                .user(diego)
                .schedule(schedule)
                .build();

        Reserve savedReserve = repoReserve.save(reserve);

        schedule.setCurrentCapacity(schedule.getCurrentCapacity() + 1);
        repoSchedule.save(schedule);

        System.out.println("Reserva creada de " + savedReserve.getUser().getFirstName());
        System.out.println("Para el servicio " + savedReserve.getSchedule().getService().getName());
        System.out.println("El dia " + savedReserve.getSchedule().getStartDatetime().toLocalDate());

        // ---------------------------------------------------------------------------
        // reservas para un servicio grupal - Yoga
        // CLIENTE 1
        User vale = repoUser.findUserByUsername("vale")
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado con username vale"));

        // Buscar horario para el servicio Yoga el 2025-04-20 a las 14:00
        LocalDateTime startTime2 = LocalDateTime.of(2025, 4, 20, 14, 0);
        Schedule schedule2 = repoSchedule.findByServiceNameAndStartDatetime("Yoga", startTime2)
                .orElseThrow(() -> new ScheduleNotFoundException("Schedule no encontrado"));

        if(schedule2.getCurrentCapacity() >= schedule2.getMaxCapacity()) {
            throw new RuntimeException("No hay espacio disponible para la reserva");
        }

        Reserve reserve2 = Reserve.builder()
                .dateReserve(LocalDateTime.now())
                .status(Reserve.StatusReserve.CONFIRMED)
                .user(vale)
                .schedule(schedule2)
                .build();

        Reserve savedReserve2 = repoReserve.save(reserve2);

        // Incrementamos la capacidad actual
        schedule2.setCurrentCapacity(schedule2.getCurrentCapacity() + 1);
        repoSchedule.save(schedule2);

        System.out.println("Reserva creada de " + savedReserve2.getUser().getFirstName());
        System.out.println("Para el servicio " + savedReserve2.getSchedule().getService().getName());
        System.out.println("El dia " + savedReserve2.getSchedule().getStartDatetime().toLocalDate());

        // ---------------------------------------------------------------------------
        // reservas para un servicio grupal - Yoga
        // CLIENTE 2
        User lidia = repoUser.findUserByUsername("lidia")
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado con username lidia"));

        if(schedule2.getCurrentCapacity() >= schedule2.getMaxCapacity()) {
            throw new RuntimeException("No hay espacio disponible para la reserva");
        }

        Reserve reserve3 = Reserve.builder()
                .dateReserve(LocalDateTime.now())
                .status(Reserve.StatusReserve.CONFIRMED)
                .user(lidia)
                .schedule(schedule2) // mismo horario
                .build();

        Reserve savedReserve3 = repoReserve.save(reserve3);

        // Incrementamos la capacidad actual - mismo horario
        schedule2.setCurrentCapacity(schedule2.getCurrentCapacity() + 1);
        repoSchedule.save(schedule2);

        System.out.println("Reserva creada de " + savedReserve3.getUser().getFirstName());
        System.out.println("Para el servicio " + savedReserve3.getSchedule().getService().getName());
        System.out.println("El dia " + savedReserve3.getSchedule().getStartDatetime().toLocalDate());
    }

}
*/
