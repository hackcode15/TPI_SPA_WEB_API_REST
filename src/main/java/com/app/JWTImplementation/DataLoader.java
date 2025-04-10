/* package com.app.JWTImplementation;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.app.JWTImplementation.dto.GenerationSchedulesDTOs.ScheduleConfigDTO;
import com.app.JWTImplementation.dto.GenerationSchedulesDTOs.TimeSlotDTO;
import com.app.JWTImplementation.exceptions.ScheduleNotFoundException;
import com.app.JWTImplementation.exceptions.UserNotFoundException;
import com.app.JWTImplementation.model.Reserve;
import com.app.JWTImplementation.model.Schedule;
import com.app.JWTImplementation.model.ServiceMainCategory;
import com.app.JWTImplementation.model.ServiceSpa;
import com.app.JWTImplementation.model.ServiceSubcategory;
import com.app.JWTImplementation.model.User;
import com.app.JWTImplementation.model.Reserve.StatusReserve;
import com.app.JWTImplementation.model.User.Role;

import com.app.JWTImplementation.repository.ReserveRepository;
import com.app.JWTImplementation.repository.ScheduleRepository;
import com.app.JWTImplementation.repository.ServiceMainCategoryRepository;
import com.app.JWTImplementation.repository.ServiceSpaRepository;
import com.app.JWTImplementation.repository.ServiceSubcategoryRepository;
import com.app.JWTImplementation.repository.UserRepository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired private UserRepository repoUser;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private ServiceMainCategoryRepository repoServiceMainCategory;
    @Autowired private ServiceSubcategoryRepository repoServiceSubcategory;
    @Autowired private ServiceSpaRepository repoServiceSpa;
    @Autowired private ScheduleRepository repoSchedule;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private ReserveRepository repoReserve;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        
        // GENERATION USERS
        //generateUsers();

        // GENERATION MAIN CATEGORY, SUBCATEGORY AND SERVICES SPA
        //generateMainCategoryAndSubcategoryAndService();

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

    private void generateMainCategoryAndSubcategoryAndService() {

        // ------------------------------------------------------------------------------------
        // MAIN CATEGORY
        ServiceMainCategory individual = ServiceMainCategory.builder()
            .name("INDIVIDUAL")
            .description("Servicios para una sola persona")
            .isGroupService(false)
            .build();

        ServiceMainCategory grupal = ServiceMainCategory.builder()
            .name("GRUPAL")
            .description("Serivicios para grupos de personas")
            .isGroupService(true)
            .build();

        repoServiceMainCategory.saveAll(List.of(individual, grupal));
        // ------------------------------------------------------------------------------------

        // ------------------------------------------------------------------------------------
        // SUBCATEGORY
        ServiceSubcategory masajes = ServiceSubcategory.builder()
            .serviceMainCategory(individual)
            .name("MASAJES")
            .build();

        ServiceSubcategory belleza = ServiceSubcategory.builder()
            .serviceMainCategory(individual)
            .name("BELLEZA")
            .build();

        ServiceSubcategory tratamientosFaciales = ServiceSubcategory.builder()
            .serviceMainCategory(individual)
            .name("TRATAMIENTOS FACIALES")
            .build();

        ServiceSubcategory tratamientosCorporales = ServiceSubcategory.builder()
            .serviceMainCategory(individual)
            .name("TRATAMIENTOS CORPORALES")
            .build();

        ServiceSubcategory otrosGrupales = ServiceSubcategory.builder()
            .serviceMainCategory(grupal)
            .name("Servicios Grupales")
            .build();

        repoServiceSubcategory.saveAll(
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
            .serviceSubcategory(masajes)
            .name("Anti-stress")
            .description(null)
            .durationMinutes(60)
            .isActive(true)
            .build();

        ServiceSpa descontracturantes = ServiceSpa.builder()
            .serviceSubcategory(masajes)
            .name("Descontracturantes")
            .description(null)
            .durationMinutes(60)
            .isActive(true)
            .build();

        // BELLEZA
        ServiceSpa liftingDePestana = ServiceSpa.builder()
            .serviceSubcategory(belleza)
            .name("Lifting de pestaña")
            .description(null)
            .durationMinutes(60)
            .isActive(true)
            .build();

        ServiceSpa depelicacionFacial = ServiceSpa.builder()
            .serviceSubcategory(belleza)
            .name("Depilación facial")
            .description(null)
            .durationMinutes(60)
            .isActive(true)
            .build();

        // TRATAMIENTOS FACIALES
        ServiceSpa puntaDeDiamante = ServiceSpa.builder()
            .serviceSubcategory(tratamientosFaciales)
            .name("Punta de Diamante")
            .description("Microexfocilación")
            .durationMinutes(60)
            .isActive(true)
            .build();

        ServiceSpa crioFrecuenciaFacial = ServiceSpa.builder()
            .serviceSubcategory(tratamientosFaciales)
            .name("Crio frecuencia facial")
            .description("Produce el 'SHOCK TERMICO'")
            .durationMinutes(60)
            .isActive(true)
            .build();

        // TRATAMIENTOS CORPORALES
        ServiceSpa velaSlim = ServiceSpa.builder()
            .serviceSubcategory(tratamientosCorporales)
            .name("VelaSlim")
            .description("Reducción de la circunferencia corporal y la celulitis")
            .durationMinutes(60)
            .isActive(true)
            .build();

        ServiceSpa dermoHealth = ServiceSpa.builder()
            .serviceSubcategory(tratamientosCorporales)
            .name("DermoHealth")
            .description("moviliza los distintos tejidos de la piel y estimula la microcirculación, generando un drenaje linfático")
            .durationMinutes(60)
            .isActive(true)
            .build();

        // GRUPAL
        ServiceSpa hidromasajes = ServiceSpa.builder()
            .serviceSubcategory(otrosGrupales)
            .name("Hidromasajes")
            .description(null)
            .durationMinutes(60)
            .isActive(true)
            .build();

        ServiceSpa yoga = ServiceSpa.builder()
            .serviceSubcategory(otrosGrupales)
            .name("Yoga")
            .description(null)
            .durationMinutes(60)
            .isActive(true)
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
            // Leer el JSON de los horarios de servicios de ese dia en concreto
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("jsonSchedules/Schedules1.json");

            if(inputStream == null) {
                System.err.println("No se pudo encontrar el archivo json");
                return;
            }

            // Deserializar JSON a lista de ScheduleConfigDTO
            List<ScheduleConfigDTO> scheduleConfigs = objectMapper.readValue(
                inputStream,
                new TypeReference<List<ScheduleConfigDTO>>() {}
            );

            // Fecha de punto de partida
            LocalDate currentDate = LocalDate.of(2025, 4, 8);
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

            List<Schedule> schedulesToSave = new ArrayList<>();

            // Para cada servicio en el JSON
            for (ScheduleConfigDTO config : scheduleConfigs) {
                
                // Buscar el servicio por nombre
                List<ServiceSpa> matchingServices = repoServiceSpa.findByName(config.getServiceName());

                if (matchingServices.isEmpty()) {
                    System.err.println("Servicio no encontrado: " + config.getServiceName());
                    continue;
                }

                ServiceSpa service = matchingServices.get(0);

                // Para cada horario definido en el JSON
                for (TimeSlotDTO slot : config.getSlots()) {
                    
                    // Parsear las horas de inicio y fin
                    LocalTime starTime = LocalTime.parse(slot.getStartTime(), timeFormatter);
                    LocalTime endTime = LocalTime.parse(slot.getEndTime(), timeFormatter);

                    //System.out.println("Hora inicio: " + starTime);
                    //System.out.println("Hora fin: " + endTime); 

                    // Crear el horario con fecha y hora
                    LocalDateTime startDateTime = LocalDateTime.of(currentDate, starTime);
                    LocalDateTime endDateTime = LocalDateTime.of(currentDate, endTime);

                    //System.out.println("Fecha/hora inicio: " + startDateTime);
                    //System.out.println("Fecha/hora fin: " + endDateTime); 

                    // Creación y adición del horario
                    Schedule schedule = Schedule.builder()
                        .service(service)
                        .startDatetime(startDateTime)
                        .endDatetime(endDateTime)
                        .maxCapacity(1)
                        .currentCapacity(0)
                        .isActive(true)
                        .build();

                    schedulesToSave.add(schedule);

                }

            }

            // Guardar todos los horarios en la base de datos
            repoSchedule.saveAll(schedulesToSave);
            System.out.println("Se generaron " + schedulesToSave.size() + " horarios");

        } catch (Exception e) {
            System.err.println("Error al leer el archivo JSON de horarios: " + e.getMessage());
            e.printStackTrace();
        }

    }

    private void generateReserve() {
    
        User lidia = repoUser.findUserByUsername("lidia")
            .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado con username vale"));

        List<ServiceSpa> serviceList = repoServiceSpa.findByName("Anti-stress");
        ServiceSpa serviceSpa = serviceList.get(0);

        Schedule schedule = repoSchedule.findById(1)
            .orElseThrow(() -> new ScheduleNotFoundException("Schedule no encontrado"));

        Reserve reserve = Reserve.builder()
            .user(lidia)
            .serviceSpa(serviceSpa)
            .schedule(schedule)
            .dateReserve(LocalDateTime.now())
            .status(StatusReserve.CONFIRMED)
            .build();

        repoReserve.save(reserve);

    }

} */