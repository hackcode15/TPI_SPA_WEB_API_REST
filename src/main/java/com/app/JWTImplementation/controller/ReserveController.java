package com.app.JWTImplementation.controller;

import java.time.LocalDateTime;
import java.util.List;

import com.app.JWTImplementation.dto.ReserveDTO;
import com.app.JWTImplementation.model.Reserve;
import com.app.JWTImplementation.model.Schedule;
import com.app.JWTImplementation.model.User;
import com.app.JWTImplementation.service.ScheduleService;
import com.app.JWTImplementation.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.app.JWTImplementation.dto.ReserveInfoDTO;
import com.app.JWTImplementation.dto.responses.ApiResponse;
import com.app.JWTImplementation.service.ReserveService;

@RestController
@RequestMapping("/api/reserve")
@Tag(name = "Reserva", description = "Controlador para las Reservas")
public class ReserveController {
    
    @Autowired private ReserveService service;
    @Autowired private UserService userService;
    @Autowired private ScheduleService scheduleService;

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
    public ResponseEntity<ApiResponse<ReserveInfoDTO>> findReserveByIdWithEntity(@PathVariable("id") Integer id) {

        ReserveInfoDTO reserveInfoDTO = service.findReserveWithEntityById(id);

        ApiResponse<ReserveInfoDTO> response = new ApiResponse<>(
            "Success",
            "Reserve All Info successfully recovered",
            reserveInfoDTO
        );

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    // nueva reserva
    @PostMapping("/new")
    @ResponseBody
    @Operation(
            summary = "Nueva Reserva",
            description = "Creación de una nueva Reserva",
            tags = {"Reserva"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Solicitud de creación con el ID del usuario, el ID del horario del Servicio y su estado",
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
    public ResponseEntity<ApiResponse<ReserveInfoDTO>> createReserve(@RequestBody ReserveDTO reserveDTO) {

        // FALTAN VALIDACIONES

        User user = userService.findUserById(reserveDTO.getUserId());
        Schedule schedule = scheduleService.findScheduleById(reserveDTO.getScheduleId());

        Reserve reserveSaved = Reserve.builder()
                .dateReserve(LocalDateTime.now())
                .user(user)
                .schedule(schedule)
                .status(Reserve.StatusReserve.CONFIRMED)
                .build();

        Reserve reserve = service.saveReserve(reserveSaved);

        ReserveInfoDTO reserveInfoDTO = service.findReserveWithEntityById(reserve.getId());

        ApiResponse<ReserveInfoDTO> response = new ApiResponse<>(
                "Success",
                "Reservation made successfully",
                reserveInfoDTO
        );

        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    // actualizar reserva
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
    public ResponseEntity<ApiResponse<String>> deleteReserve(@PathVariable("id") Integer id) {

        service.deleteReserveById(id);

        ApiResponse<String> response = new ApiResponse<>(
                "Success",
                "Reservation deleted successfully",
                null
        );

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    // listar reservas de un cliente en especifico

    // listar reservas de un servicio en especifico


}
