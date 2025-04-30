package com.app.JWTImplementation.controller;

import com.app.JWTImplementation.dto.ScheduleInfoDTO;
import com.app.JWTImplementation.dto.generationSchedulesDTOs.AvailabilityResponseDTO;
import com.app.JWTImplementation.dto.generationSchedulesDTOs.TimeSlotDTO;
import com.app.JWTImplementation.dto.responses.ApiResponse;
import com.app.JWTImplementation.repository.ScheduleRepository;
import com.app.JWTImplementation.service.ScheduleGeneratorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/availability")
@Tag(name = "Disponibilidad", description = "Controlador para los Horarios generados en memoria")
public class AvailabilityController {

    @Autowired
    private ScheduleGeneratorService scheduleGeneratorService;

    @GetMapping
    @Operation(
            summary = "Generador de horarios en memoria",
            description = "Genera horarios en un formato json mediante el ID del servicio, la fecha y el numero total de dias",
            tags = {"Disponibilidad"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Horarios generados exitosamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = AvailabilityResponseDTO.class
                                    )
                            )
                    )
            }
    )
    public ResponseEntity<ApiResponse<List<AvailabilityResponseDTO>>> getAvailability(
            @RequestParam Integer serviceId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(defaultValue = "30") int days) {

        if (date == null) {
            date = LocalDate.now();
        }

        List<ScheduleInfoDTO> schedules = scheduleGeneratorService.generateAvailableSchedules(date, serviceId, days);

        // Convertir a DTO de respuesta agrupado por dia
        Map<LocalDate, List<ScheduleInfoDTO>> byDate = schedules.stream()
                .collect(Collectors.groupingBy(ScheduleInfoDTO::getStartDate));

        List<AvailabilityResponseDTO> availables = byDate.entrySet().stream()
                .map(entry -> new AvailabilityResponseDTO(
                        entry.getKey(),
                        entry.getValue().get(0).getService(),
                        entry.getValue().stream()
                                .map(slot -> new TimeSlotDTO(
                                        slot.getStartTime(),
                                        slot.getEndTime(),
                                        slot.getMaxCapacity() - slot.getCurrentCapacity()
                                ))
                                .collect(Collectors.toList())
                ))
                .sorted(Comparator.comparing(AvailabilityResponseDTO::getDate))
                .collect(Collectors.toList());

        ApiResponse<List<AvailabilityResponseDTO>> response = new ApiResponse<>(
                "Success",
                "Schedules generated successfully",
                availables
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
