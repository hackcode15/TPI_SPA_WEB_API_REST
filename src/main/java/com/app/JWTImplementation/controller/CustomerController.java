package com.app.JWTImplementation.controller;

import com.app.JWTImplementation.dto.responses.ApiResponse;
import com.app.JWTImplementation.dto.responses.HistoryReservationResponse;
import com.app.JWTImplementation.dto.responses.UserReservationHistoryResponse;
import com.app.JWTImplementation.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// ░░░░░░░░░░░░░░ACCESIBLE SOLO PARA CLIENTES (ENDPOINTS PERSONALES)░░░░░░░░░░░░░░░░░
@PreAuthorize("hasRole('CUSTOMER')")
@RestController
@RequestMapping("/api/user/customer/")
@Tag(name = "Cliente", description = "Controlador para los Clientes")
public class CustomerController {

    @Autowired private UserService service;


    @ResponseBody
    @Operation(
            summary = "Ver el historial de Reservas de un Cliente",
            description = "Lista todas las reservas realizadas de un usuario en especifico por su ID",
            tags = {"Cliente"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Historial de Reservas recuperados exitosamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = UserReservationHistoryResponse.class
                                    )
                            )
                    )
            }
    )
    @GetMapping("/reservation-history/{userId}")
    public ResponseEntity<ApiResponse<HistoryReservationResponse>> getAllReservationHistory(
            @PathVariable("userId") Integer userId
    ) {

        HistoryReservationResponse history = service.findAllUserReservationHistoryById(userId);

        ApiResponse<HistoryReservationResponse> response = new ApiResponse<>(
                "Success",
                "Reservation History recovered successfully",
                history
        );

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @Operation(
            summary = "Cancelacion de Reservas",
            description = "Cancela la reserva, validando que solo puedan cancelar reservas propias",
            tags = {"Cliente"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Reservacion cancelada exitosamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ApiResponse.class
                                    )
                            )
                    )
            }
    )
    @PutMapping("/{userId}/cancel-reservation/{reservationId}")
    public ResponseEntity<ApiResponse<String>> cancelReservationById(
            @PathVariable("userId") Integer userId,
            @PathVariable("reservationId") Integer reservationId
    ) {

        boolean cancelReservationStatus = service.cancelReservationById(userId, reservationId);

        ApiResponse<String> response = new ApiResponse<>(
                "Success",
                "Reservation Cancelled",
                null
        );

        return new ResponseEntity<>(response, HttpStatus.OK);

    }


}
