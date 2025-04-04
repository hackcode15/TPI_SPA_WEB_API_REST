package com.app.JWTImplementation.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.JWTImplementation.dto.ReserveDTO;
import com.app.JWTImplementation.dto.ReserveRequestDTO;
import com.app.JWTImplementation.dto.responses.ApiResponse;
import com.app.JWTImplementation.model.Reserve;
import com.app.JWTImplementation.service.ReserveService;

@RestController
@RequestMapping("/api/reserve")
public class ReserveController {
    
    @Autowired
    private ReserveService service;

    @GetMapping
    @ResponseBody
    public ResponseEntity<ApiResponse<List<ReserveDTO>>> getReserves() {
        
        List<Reserve> reserves = service.findAllReserves();

        List<ReserveDTO> reserveDTO = reserves.stream()
            .map(reserve -> {
                
                ReserveDTO dto = ReserveDTO.builder()
                    .id(reserve.getId())
                    .reservationDate(reserve.getReservationDate())
                    .nameUser(reserve.getUser().getFirstName() + ", " + reserve.getUser().getLastName())
                    .nameServiceSpa(reserve.getService().getName())
                    .dateReserve(reserve.getSchedule().getDate())
                    .hourReserve(reserve.getSchedule().getHour())
                    .build();

                return dto;

            }).collect(Collectors.toList());

        ApiResponse<List<ReserveDTO>> response = new ApiResponse<>(
            "Success",
            "Reserve retrived succesfully",
            reserveDTO
        );

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    // GET - FindReserveById
    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<ApiResponse<ReserveDTO>> getReserve (@PathVariable("id") Integer id) {
        
        Reserve reserve = service.findReserveById(id);

        ReserveDTO reserveDTO = ReserveDTO.builder()
            .id(reserve.getId())
            .reservationDate(reserve.getReservationDate())
            .nameUser(reserve.getUser().getFirstName() + ", " + reserve.getUser().getLastName())
            .nameServiceSpa(reserve.getService().getName())
            .dateReserve(reserve.getSchedule().getDate())
            .hourReserve(reserve.getSchedule().getHour())
            .build();

        ApiResponse<ReserveDTO> response = new ApiResponse<>(
            "Success",
            "Reserve created successfully",
            reserveDTO
        );

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    // POST - new reserve
    @PostMapping("/new")
    @ResponseBody
    public ResponseEntity<ApiResponse<ReserveDTO>> newReserve (@RequestBody ReserveRequestDTO reserveDetails) {
        
        Reserve reserve = service.saveReserve(reserveDetails);

        ReserveDTO reserveDTO = ReserveDTO.builder()
            .id(reserve.getId())
            .reservationDate(reserve.getReservationDate())
            .nameUser(reserve.getUser().getFirstName() + ", " + reserve.getUser().getLastName())
            .nameServiceSpa(reserve.getService().getName())
            .dateReserve(reserve.getSchedule().getDate())
            .hourReserve(reserve.getSchedule().getHour())
            .build();

        ApiResponse<ReserveDTO> response = new ApiResponse<>(
            "Success",
            "Reserve created successfully",
            reserveDTO
        );

        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    // PUT - updateReserveById

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<String>> deleteById (@PathVariable("id") Integer id) {
        
        service.deleteReserveById(id);

        ApiResponse<String> response = new ApiResponse<>(
            "Success",
            "Reserve deleted successfully",
            null
        );

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

}
