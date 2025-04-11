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
import com.app.JWTImplementation.repository.ServiceCategoryRepository;
import com.app.JWTImplementation.repository.UserRepository;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UserRepository repoUser;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ServiceCategoryRepository repoServiceCategory;
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
        //generateServiceCategoryAndServiceSpa();

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
                .role(Role.ADMIN)
                .build();

        // USER
        User vale = User.builder()
                .username("vale")
                .password(passwordEncoder.encode("2016"))
                .firstName("Valentina Lara")
                .lastName("Gomez")
                .role(Role.USER)
                .build();

        // USER
        User lidia = User.builder()
                .username("lidia")
                .password(passwordEncoder.encode("1984"))
                .firstName("Lidia Celeste")
                .lastName("Salinas")
                .role(Role.USER)
                .build();

        repoUser.saveAll(List.of(diego, vale, lidia));

    }

    private void generateServiceCategoryAndServiceSpa() {

        // ------------------------------------------------------------------------------------
        // CATEGORY

        ServiceCategory masajes = ServiceCategory.builder()
                .name("MASAJE")
                .description("Servicios de Masajes")
                .isGroupService(false)
                .build();


        ServiceCategory belleza = ServiceCategory.builder()
                .name("BELLEZA")
                .description("Servicios de Belleza")
                .isGroupService(false)
                .build();

        ServiceCategory tratamientosFaciales = ServiceCategory.builder()
                .name("TRATAMIENTO FACIAL")
                .description("Servicios de Tratamientos Faciales")
                .isGroupService(false)
                .build();

        ServiceCategory tratamientosCorporales = ServiceCategory.builder()
                .name("TRATAMIENTO CORPORAL")
                .description("Servicios de Tratamientos Corporales")
                .isGroupService(false)
                .build();

        ServiceCategory otrosGrupales = ServiceCategory.builder()
                .name("SERVICIOS GRUPALES")
                .description("Servicios Grupales")
                .isGroupService(true)
                .build();

        repoServiceCategory.saveAll(
                List.of(
                        masajes,
                        belleza,
                        tratamientosFaciales,
                        tratamientosCorporales,
                        otrosGrupales
                )
        );
        // ------------------------------------------------------------------------------------

        // ------------------------------------------------------------------------------------
        // SERVICES
        // INDIVIDUALES
        // MASAJES
        ServiceSpa antiStress = ServiceSpa.builder()
                .name("Anti-stress")
                .description(null)
                .durationMinutes(60)
                .isActive(true)
                .category(masajes)
                .build();

        ServiceSpa descontracturantes = ServiceSpa.builder()
                .name("Descontracturantes")
                .description(null)
                .durationMinutes(60)
                .isActive(true)
                .category(masajes)
                .build();

        // BELLEZA
        ServiceSpa liftingDePestana = ServiceSpa.builder()
                .name("Lifting de pestaña")
                .description(null)
                .durationMinutes(60)
                .isActive(true)
                .category(belleza)
                .build();

        ServiceSpa depelicacionFacial = ServiceSpa.builder()
                .name("Depilación facial")
                .description(null)
                .durationMinutes(60)
                .isActive(true)
                .category(belleza)
                .build();

        // TRATAMIENTOS FACIALES
        ServiceSpa puntaDeDiamante = ServiceSpa.builder()
                .name("Punta de diamante")
                .description(null)
                .durationMinutes(60)
                .isActive(true)
                .category(tratamientosFaciales)
                .build();

        ServiceSpa crioFrecuenciaFacial = ServiceSpa.builder()
                .name("Crio frecuencia facial")
                .description(null)
                .durationMinutes(60)
                .isActive(true)
                .category(tratamientosFaciales)
                .build();

        // TRATAMIENTOS CORPORALES
        ServiceSpa velaSlim = ServiceSpa.builder()
                .name("Vela slim")
                .description(null)
                .durationMinutes(60)
                .isActive(true)
                .category(tratamientosCorporales)
                .build();

        ServiceSpa dermoHealth = ServiceSpa.builder()
                .name("dermo-health")
                .description(null)
                .durationMinutes(60)
                .isActive(true)
                .category(tratamientosCorporales)
                .build();

        // GRUPAL
        ServiceSpa hidromasajes = ServiceSpa.builder()
                .name("Hidromasajes")
                .description(null)
                .durationMinutes(60)
                .isActive(true)
                .category(otrosGrupales)
                .build();

        ServiceSpa yoga = ServiceSpa.builder()
                .name("Yoga")
                .description(null)
                .durationMinutes(60)
                .isActive(true)
                .category(otrosGrupales)
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

            // Parsear la fecha del dia
            LocalDate scheduleDate = LocalDate.parse(dailySchedule.getDay());
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

            List<Schedule> schedulesToSave = new ArrayList<>();

            // Para cada servicio en el JSON
            for (ServiceScheduleDTO serviceSchedule : dailySchedule.getServices()) {

                // Busca el servicio por nombre
                List<ServiceSpa> matchingServices = repoServiceSpa.findByName(serviceSchedule.getServiceName());

                if (matchingServices.isEmpty()) {
                    System.err.println("Servicio no encontrado: " + serviceSchedule.getServiceName());
                    continue;
                }

                ServiceSpa service = matchingServices.get(0);

                // Para cada slot definido en el JSON (solo procesamos los disponibles)
                for (TimeSlotAvailabilityDTO slot : serviceSchedule.getSlots()) {

                    if (!slot.isAvailable()) {
                        continue; // Saltar slots no disponibles
                    }

                    // Parsear la hora de inicio
                    LocalTime startTime = LocalTime.parse(slot.getStartTime(), timeFormatter);

                    // Calcular la hora de fin basada en la duracion del servicio
                    LocalTime endTime = startTime.plusMinutes(service.getDurationMinutes());

                    // Crear el horario con fecha y hora
                    LocalDateTime startDateTime = LocalDateTime.of(scheduleDate, startTime);
                    LocalDateTime endDateTime = LocalDateTime.of(scheduleDate, endTime);

                    // Determinar capacidad máxima (1 para individuales, otro valor para grupales)
                    int maxCapacity = service.getCategory().getIsGroupService() ? 10 : 1;

                    // Creacion y adicion del horario
                    Schedule schedule = Schedule.builder()
                            .service(service)
                            .startDatetime(startDateTime)
                            .endDatetime(endDateTime)
                            .maxCapacity(maxCapacity)
                            .currentCapacity(0)
                            .isActive(true)
                            .build();

                    schedulesToSave.add(schedule);

                }

            }

            // Guardar todos los horarios en la base de datos
            repoSchedule.saveAll(schedulesToSave);
            System.out.println("Horarios generados correctamente");
            System.out.println("Se generaron " + schedulesToSave.size() + " horarios para el dia " + dailySchedule.getDay());

        } catch (Exception e) {
            System.err.println("Error al leer el archivo JSON de horarios: " + e.getMessage());
            e.printStackTrace();
        }

    }

    private void generateReserve() {

        User lidia = repoUser.findUserByUsername("vale")
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado con username vale"));

        Schedule schedule = repoSchedule.findById(1)
                .orElseThrow(() -> new ScheduleNotFoundException("Schedule no encontrado"));

        if(schedule.getCurrentCapacity() >= schedule.getMaxCapacity()) {
            throw new RuntimeException("No hay espacio disponible para la reserva");
        }

        Reserve reserve = Reserve.builder()
                .user(lidia)
                .schedule(schedule)
                .status(Reserve.StatusReserve.CONFIRMED)
                .build();

        Reserve savedReserve = repoReserve.save(reserve);

        schedule.setCurrentCapacity(schedule.getCurrentCapacity() + 1);
        repoSchedule.save(schedule);

        System.out.println("Reserva creada para el servicio: "
                + savedReserve.getService().getName());
        System.out.println("Horario: "
                + savedReserve.getSchedule().getStartDatetime() + " a "
                + savedReserve.getSchedule().getEndDatetime());

    }


}
*/
