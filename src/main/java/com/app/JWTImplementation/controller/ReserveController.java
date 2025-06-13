package com.app.JWTImplementation.controller;

import java.time.LocalDate;
import java.util.List;

import com.app.JWTImplementation.dto.ReserveDTO;
import com.app.JWTImplementation.dto.responses.TotalIncomeByProfessional;
import com.app.JWTImplementation.dto.responses.TotalIncomeByService;
import com.app.JWTImplementation.model.Reserve;
import com.app.JWTImplementation.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.app.JWTImplementation.dto.ReserveInfoDTO;
import com.app.JWTImplementation.dto.responses.ApiResponse;

@RestController
@RequestMapping("/api/reserve")
@Tag(name = "Reserva", description = "Controlador para las Reservas")
public class ReserveController {
    
    @Autowired private ReserveService service;
    @Autowired private UserService userService;
    @Autowired private ScheduleService scheduleService;
    @Autowired private AdminService adminService;

    @GetMapping("/list-info")
    @ResponseBody
    @Operation(
            summary = "Ver todas las Reservas con su información completa",
            description = "Lista todas las Reservas con su información completa, incluyendo el usuario, horario y servicio asignado",
            tags = {"Reserva"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Reservas Toda la información recuperada correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ReserveInfoDTO.class
                                    )
                            )
                    )
            }
    )
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<ReserveInfoDTO>>> getAllReserveWithEntities() {
        
        List<ReserveInfoDTO> reservesDTO = service.findAllReservesWhitEntities();

        ApiResponse<List<ReserveInfoDTO>> response = new ApiResponse<>(
            "Success",
            "Reserves All Info retrived succesfully",
            reservesDTO
        );

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    // obtener reserva por id
    @GetMapping("/info/{id}")
    @ResponseBody
    @Operation(
            summary = "Mostrar Reserva con toda su información por su ID",
            description = "Busca la Reserva por su ID y muestra toda la información, incluyendo la de sus relaciones",
            tags = {"Horario"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Reservar Toda la información recuperada correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ReserveInfoDTO.class
                                    )
                            )
                    )
            }
    )
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<ReserveInfoDTO>> findReserveByIdWithEntity(@PathVariable("id") Integer id) {

        ReserveInfoDTO reserveInfoDTO = service.findReserveWithEntityById(id);

        ApiResponse<ReserveInfoDTO> response = new ApiResponse<>(
            "Success",
            "Reserve All Info successfully recovered",
            reserveInfoDTO
        );

        return new ResponseEntity<>(response, HttpStatus.OK);

    }


    @PostMapping("/new")
    @Operation(
            summary = "Nueva Reserva",
            description = "Creación de una nueva Reserva",
            tags = {"Reserva"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "1° Forma de solicitud de creación con el ID del usuario, el ID del horario (existente en BD) y el ID del servicio" +
                            "2° Forma de solicitud de creación con el ID del usuario, el horario seleccionado desde el frontend y el ID del servicio",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = ReserveDTO.class
                            )
                    )
            ),
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "201",
                            description = "Reserva creada exitosamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ReserveInfoDTO.class
                                    )
                            )
                    )
            }
    )
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ApiResponse<ReserveInfoDTO>> createReservation(@Valid @RequestBody ReserveDTO reserveDetails) {

        Reserve reserve = service.saveReserve(reserveDetails);
        ReserveInfoDTO reserveDTO = service.findReserveWithEntityById(reserve.getId());

        ApiResponse<ReserveInfoDTO> response = new ApiResponse<>(
                "Success",
                "Reserve created successfully",
                reserveDTO
        );

        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    // actualizar reserva
    // NO hace falta - solo dar de baja
    @PutMapping("/update/{id}")
    @ResponseBody
    @Operation(
            summary = "Editar una Reserva por su ID",
            description = "Actualiza todos los datos de la Reserva si existe",
            tags = {"Reserva"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Solicitud de modificación con su ID, ID del Usuario, ID del Horario y su estado",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = ReserveDTO.class
                            )
                    )
            ),
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Reserva actualizada exitosamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ReserveInfoDTO.class
                                    )
                            )
                    )
            }
    )
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ApiResponse<ReserveInfoDTO>> updateReserve(
            @PathVariable("id") Integer id,
            @RequestBody ReserveDTO reserveDTO) {

        // FALTAN VALIDACIONES

        Reserve reserve = service.updateReserve(id, reserveDTO);

        ReserveInfoDTO reserveInfoDTO = service.findReserveWithEntityById(reserve.getId());

        ApiResponse<ReserveInfoDTO> response = new ApiResponse<>(
                "Success",
                "Reservation updated successfully",
                reserveInfoDTO
        );

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    // eliminar reserva
    @DeleteMapping("/delete/{id}")
    @Operation(
            summary = "Borrar Reserva",
            description = "Eliminar una Reserva por su ID si existe",
            tags = {"Reserva"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Reserva eliminada exitosamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ApiResponse.class
                                    )
                            )
                    )
            }
    )
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<String>> deleteReserve(@PathVariable("id") Integer id) {

        service.deleteReserveById(id);

        ApiResponse<String> response = new ApiResponse<>(
                "Success",
                "Reservation deleted successfully",
                null
        );

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSIONAL')")
    @GetMapping("/total-income/professional")
    @ResponseBody
    public ResponseEntity<ApiResponse<TotalIncomeByProfessional>> getTotalIncomeByProfessional(
            @RequestParam("idProfessional") Integer idProfessional,
            @RequestParam("startDate") LocalDate startDate,
            @RequestParam("endDate") LocalDate endDate
    ) {

        ApiResponse<TotalIncomeByProfessional> response = new ApiResponse<>(
                "Success",
                "Retrived total income of reservations successfully",
                adminService.getTotalIncomeByProfessional(idProfessional, startDate, endDate)
        );

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/total-income/service")
    @ResponseBody
    public ResponseEntity<ApiResponse<TotalIncomeByService>> getTotalIncomeByService(
            @RequestParam("idService") Integer idService,
            @RequestParam("startDate") LocalDate startDate,
            @RequestParam("endDate") LocalDate endDate
    ) {

        ApiResponse<TotalIncomeByService> response = new ApiResponse<>(
                "Success",
                "Retrived total incomes by service",
                adminService.getTotalIncomeByService(idService, startDate, endDate)
        );

        return new ResponseEntity<>(response, HttpStatus.OK);

    }


}
