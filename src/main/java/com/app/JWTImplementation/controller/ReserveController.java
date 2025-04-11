package com.app.JWTImplementation.controller;

import java.time.LocalDateTime;
import java.util.List;

import com.app.JWTImplementation.dto.ReserveDTO;
import com.app.JWTImplementation.model.Reserve;
import com.app.JWTImplementation.model.Schedule;
import com.app.JWTImplementation.model.User;
import com.app.JWTImplementation.service.ScheduleService;
import com.app.JWTImplementation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.app.JWTImplementation.dto.ReserveInfoDTO;
import com.app.JWTImplementation.dto.responses.ApiResponse;
import com.app.JWTImplementation.service.ReserveService;

@RestController
@RequestMapping("/api/reserve")
public class ReserveController {
    
    @Autowired private ReserveService service;
    @Autowired private UserService userService;
    @Autowired private ScheduleService scheduleService;

    @GetMapping("/list-info")
    @ResponseBody
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
