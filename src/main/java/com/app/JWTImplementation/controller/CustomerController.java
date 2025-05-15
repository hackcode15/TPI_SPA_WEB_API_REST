package com.app.JWTImplementation.controller;

import com.app.JWTImplementation.dto.customer.CustomerRequestDTO;
import com.app.JWTImplementation.dto.customer.CustomerResponseDTO;
import com.app.JWTImplementation.dto.responses.ApiResponse;
import com.app.JWTImplementation.dto.responses.HistoryReservationResponse;
import com.app.JWTImplementation.dto.responses.UserReservationHistoryResponse;
import com.app.JWTImplementation.model.Customer;
import com.app.JWTImplementation.service.CustomerService;
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


@RestController
@RequestMapping("/api/user/customer/")
@Tag(name = "Cliente", description = "Controlador para los Clientes")
public class CustomerController {

    @Autowired private UserService service;

    @Autowired private CustomerService customerService;

    // ░░░░░░░░░░░░░░ACCESIBLE SOLO PARA CLIENTES (ENDPOINTS PERSONALES)░░░░░░░░░░░░░░░░░

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
    @PreAuthorize("hasRole('CUSTOMER')")
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
    @PreAuthorize("hasRole('CUSTOMER')")
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

    // CUSTOMERS

    // Accesible solo para la doctora (ADMIN)
    @GetMapping("/list")
    @ResponseBody
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<CustomerResponseDTO>>> getCustomers() {

        ApiResponse<List<CustomerResponseDTO>> response = new ApiResponse<>(
                "Success",
                "Customers retrived successfully",
                customerService.findAllCustomers()
        );

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @GetMapping("/{id}")
    @ResponseBody
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ApiResponse<CustomerResponseDTO>> getCustomer(@PathVariable("id") Integer id) {

        ApiResponse<CustomerResponseDTO> response = new ApiResponse<>(
                "Success",
                "Customer retrived successfully",
                customerService.findCustomerById(id)
        );

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PutMapping("/update/{id}")
    @ResponseBody
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ApiResponse<CustomerResponseDTO>> editCustomerById(
            @PathVariable("id") Integer id,
            @RequestBody CustomerRequestDTO customerDetails
            ) {

        ApiResponse<CustomerResponseDTO> response = new ApiResponse<>(
                "Success",
                "Customer retrived successfully",
                customerService.updateCustomer(id, customerDetails)
        );

        return new ResponseEntity<>(response, HttpStatus.OK);

    }


}
