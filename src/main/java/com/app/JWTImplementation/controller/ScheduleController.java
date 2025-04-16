package com.app.JWTImplementation.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.app.JWTImplementation.dto.ScheduleInfoDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.JWTImplementation.dto.ScheduleDTO;
import com.app.JWTImplementation.dto.responses.ApiResponse;
import com.app.JWTImplementation.model.Schedule;
import com.app.JWTImplementation.service.ScheduleService;

@RestController
@RequestMapping("/api/schedule")
@Tag(name = "Horario", description = "Controlador para los Horarios")
public class ScheduleController {
    
    @Autowired
    private ScheduleService service;

    @GetMapping("/list-info")
    @ResponseBody
    @Operation(
            summary = "Ver todos los Horarios con su información completa",
            description = "Lista todos los Horarios con toda su información y su Servicio asignado",
            tags = {"Horario"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Horarios Toda la información recuperada exitosamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ScheduleInfoDTO.class
                                    )
                            )
                    )
            }
    )
    public ResponseEntity<ApiResponse<List<ScheduleInfoDTO>>> getAllSchedulesWithEntities() {

        List<ScheduleInfoDTO> schedulesInfoDTO = service.findAllSchedulesWithEntities();

        ApiResponse<List<ScheduleInfoDTO>> response = new ApiResponse<>(
            "Success",
            "Schedules All Info retrived succesfully",
            schedulesInfoDTO
        );

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @GetMapping("/info/{id}")
    @ResponseBody
    @Operation(
            summary = "Mostrar Horario con toda su información con su ID",
            description = "Busca el horario por su ID y muestra toda su información con su Servicio asociado",
            tags = {"Horario"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Horario Toda la información recuperada exitosamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ScheduleInfoDTO.class
                                    )
                            )
                    )
            }
    )
    public ResponseEntity<ApiResponse<ScheduleInfoDTO>> getScheduleByIdWithEntity(@PathVariable("id") Integer id) {

        ScheduleInfoDTO scheduleInfoDTO = service.findScheduleByIdWithEntity(id);

        ApiResponse<ScheduleInfoDTO> response = new ApiResponse<>(
            "Success",
            "Schedule All Info successfully recovered",
            scheduleInfoDTO
        );

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @GetMapping("/list")
    @ResponseBody
    @Operation(
            summary = "Ver todos los Horarios",
            description = "Lista todos los horarios cargados (solo su información)",
            tags = {"Horario"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Horarios recuperados exitosamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ScheduleDTO.class
                                    )
                            )
                    )
            }
    )
    public ResponseEntity<ApiResponse<List<ScheduleDTO>>> getAllSchedules() {
        
        List<Schedule> schedules = service.findAllSchedules();

        List<ScheduleDTO> schedulesDTO = schedules.stream()
            .map(schedule -> {
                
                ScheduleDTO dto = ScheduleDTO.builder()
                    .id(schedule.getId())
                    .startDate(schedule.getStartDatetime().toLocalDate())
                    .startTime(schedule.getStartDatetime().toLocalTime())
                    .endDate(schedule.getEndDatetime().toLocalDate())
                    .endTime(schedule.getEndDatetime().toLocalTime())
                    .maxCapacity(schedule.getMaxCapacity())
                    .currentCapacity(schedule.getCurrentCapacity())
                    .isActive(schedule.getIsActive())
                    .build();

                return dto;

            }).collect(Collectors.toList());

        ApiResponse<List<ScheduleDTO>> response = new ApiResponse<>(
            "Success",
            "Schedules retrived succesfully",
            schedulesDTO
        );

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @GetMapping("/{id}")
    @ResponseBody
    @Operation(
            summary = "Mostrar Horario por su ID",
            description = "Busca el Horario por su ID y muestra solo su información",
            tags = {"Horario"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Horario recuperado exitosamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ScheduleDTO.class
                                    )
                            )
                    )
            }
    )
    public ResponseEntity<ApiResponse<ScheduleDTO>> getScheduleById(@PathVariable("id") Integer id) {
        
        Schedule schedule = service.findScheduleById(id);

        ScheduleDTO scheduleDTO = ScheduleDTO.builder()
            .id(schedule.getId())
            .startDate(schedule.getStartDatetime().toLocalDate())
            .startTime(schedule.getStartDatetime().toLocalTime())
            .endDate(schedule.getEndDatetime().toLocalDate())
            .endTime(schedule.getStartDatetime().toLocalTime())
            .maxCapacity(schedule.getMaxCapacity())
            .currentCapacity(schedule.getCurrentCapacity())
            .isActive(schedule.getIsActive())
            .build();

        ApiResponse<ScheduleDTO> response = new ApiResponse<>(
            "Success",
            "Schedule successfully recovered",
            scheduleDTO
        );

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    // Get by Date
    @GetMapping("/info-date/{date}")
    @ResponseBody
    @Operation(
            summary = "Ver todos los Horarios en un fecha especifica (yyyy-mm-dd)",
            description = "Lista todos los horarios disponibles y no disponibles en la fecha especificada",
            tags = {"Horario"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Horarios de fecha recuperados exitosamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ScheduleInfoDTO.class
                                    )
                            )
                    )
            }
    )
    public ResponseEntity<ApiResponse<List<ScheduleInfoDTO>>> getSchedulesByDate(@PathVariable("date") LocalDate date) {

        List<ScheduleInfoDTO> schedulesInfoDTO = service.findSchedulesByDate(date);

        ApiResponse<List<ScheduleInfoDTO>> response = new ApiResponse<>(
                "Succes",
                "Schedules of date retrived succesfully",
                schedulesInfoDTO
        );

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @GetMapping("/service/{idService}/date/{date}")
    @ResponseBody
    @Operation(
            summary = "Ver todos los horarios de un Servicio en un fecha especifica",
            description = "Lista todos los horarios disponibles y no disponibles del Servicio en la fecha especificada",
            tags = {"Horario"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Horarios del servicio de la fecha recuperados con éxito",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ScheduleInfoDTO.class
                                    )
                            )
                    )
            }
    )
    public ResponseEntity<ApiResponse<List<ScheduleInfoDTO>>> getSchedulesByServiceAndDate(
            @PathVariable("idService") Integer idService,
            @PathVariable("date") LocalDate date) {

        List<ScheduleInfoDTO> schedulesInfoDTO = service.findSchedulesOfServiceByDate(date, idService);

        ApiResponse<List<ScheduleInfoDTO>> response = new ApiResponse<>(
                "Sucess",
                "Schedules of service and date retrived succesfully",
                schedulesInfoDTO
        );

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    // UPDATE - no disponible de momento

    @DeleteMapping("/delete/{id}")
    @Operation(
            summary = "Borrar un Horario",
            description = "Elimina por su ID si éxiste",
            tags = {"Horario"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Horario eliminado exitosamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ScheduleDTO.class
                                    )
                            )
                    )
            }
    )
    public ResponseEntity<ApiResponse<String>> deleteSchedule(@PathVariable("id") Integer id) {

        service.deleteById(id);

        ApiResponse<String> response = new ApiResponse<>(
            "Success",
            "Schedule deleted successfully",
            null
        );

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

}
