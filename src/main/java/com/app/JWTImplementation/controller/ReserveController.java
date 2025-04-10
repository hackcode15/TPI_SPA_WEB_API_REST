package com.app.JWTImplementation.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.JWTImplementation.dto.ReserveDTO;
import com.app.JWTImplementation.dto.responses.ApiResponse;
import com.app.JWTImplementation.model.Reserve;
import com.app.JWTImplementation.service.ReserveService;

@RestController
@RequestMapping("/api/reserve")
public class ReserveController {
    
    @Autowired private ReserveService service;

    
    @GetMapping
    @ResponseBody
    public ResponseEntity<ApiResponse<List<ReserveDTO>>> getAllReserveWithEntities() {
        
        List<ReserveDTO> reservesDTO = service.findAllReservesWhitEntities();

        ApiResponse<List<ReserveDTO>> response = new ApiResponse<>(
            "Success",
            "Reserves retrived succesfully",
            reservesDTO
        );

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

}
