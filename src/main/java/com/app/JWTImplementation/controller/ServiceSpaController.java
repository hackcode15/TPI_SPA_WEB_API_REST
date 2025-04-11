package com.app.JWTImplementation.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.app.JWTImplementation.dto.ServiceSpaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.JWTImplementation.dto.ServiceSpaInfoDTO;
import com.app.JWTImplementation.dto.responses.ApiResponse;
import com.app.JWTImplementation.model.ServiceSpa;
import com.app.JWTImplementation.service.ServiceSpaService;

@RestController
@RequestMapping("/api/service-spa")
public class ServiceSpaController {

    @Autowired
    private ServiceSpaService service;

    @GetMapping("/list-info")
    @ResponseBody
    public ResponseEntity<ApiResponse<List<ServiceSpaInfoDTO>>> getAllServicesSpaInfo() {

        List<ServiceSpaInfoDTO> servicesSpa = service.findAllServicesWhitEntities();

        ApiResponse<List<ServiceSpaInfoDTO>> response = new ApiResponse<>(
                "Success",
                "Servicies Spa All Info retrived succesfully",
                servicesSpa
        );

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @GetMapping("/info/{id}")
    @ResponseBody
    public ResponseEntity<ApiResponse<ServiceSpaInfoDTO>> getServiceSpa(@PathVariable("id") Integer id) {

        ServiceSpaInfoDTO serviceSpaInfoDTO = service.findServiceWithEntityById(id);

        ApiResponse<ServiceSpaInfoDTO> response = new ApiResponse<>(
                "Success",
                "Service Spa All Info successfully recovered",
                serviceSpaInfoDTO
        );

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @GetMapping("/list")
    @ResponseBody
    public ResponseEntity<ApiResponse<List<ServiceSpaDTO>>> getAllServicesSpa() {

        List<ServiceSpa> servicesSpa = service.findAllServicesSpa();

        List<ServiceSpaDTO> servicesSpaDTO = servicesSpa.stream()
                .map(serviceSpa -> {

                    ServiceSpaDTO dto = ServiceSpaDTO.builder()
                            .id(serviceSpa.getId())
                            .name(serviceSpa.getName())
                            .description(serviceSpa.getDescription())
                            .durationMinutes(serviceSpa.getDurationMinutes())
                            .isActive(serviceSpa.getIsActive())
                            .build();

                    return dto;

                }).collect(Collectors.toList());

        ApiResponse<List<ServiceSpaDTO>> response = new ApiResponse<>(
                "Success",
                "Servicies Spa retrived succesfully",
                servicesSpaDTO
        );

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<ApiResponse<ServiceSpaDTO>> getServiceSpaById(@PathVariable("id") Integer id) {

        ServiceSpa serviceSpa = service.findServiceSpaById(id);

        ServiceSpaDTO serviceSpaDTO = ServiceSpaDTO.builder()
                .id(serviceSpa.getId())
                .name(serviceSpa.getName())
                .description(serviceSpa.getDescription())
                .durationMinutes(serviceSpa.getDurationMinutes())
                .isActive(serviceSpa.getIsActive())
                .build();

        ApiResponse<ServiceSpaDTO> response = new ApiResponse<>(
                "Success",
                "Service Spa successfully recovered",
                serviceSpaDTO
        );

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PutMapping("/update/{id}")
    @ResponseBody
    public ResponseEntity<ApiResponse<ServiceSpaInfoDTO>> updateServiceSpa(
            @PathVariable("id") Integer id,
            @RequestBody ServiceSpaDTO serviceSpaDetails) {

        ServiceSpa serviceSpaUpdate = service.updateServiceSpa(id, serviceSpaDetails);

        ServiceSpaInfoDTO serviceSpaInfoDTO = ServiceSpaInfoDTO.builder()
                .id(serviceSpaUpdate.getId())
                .name(serviceSpaUpdate.getName())
                .description(serviceSpaUpdate.getDescription())
                .durationMinutes(serviceSpaUpdate.getDurationMinutes())
                .isActive(serviceSpaUpdate.getIsActive())
                .build();

        ApiResponse<ServiceSpaInfoDTO> response = new ApiResponse<>(
                "Success",
                "Updated service spa successfully",
                serviceSpaInfoDTO
        );

        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<String>> deleteServiceSpa(@PathVariable("id") Integer id) {

        service.deleteServiceSpaById(id);

        ApiResponse<String> response = new ApiResponse<>(
                "Success",
                "Service spa deleted successfully",
                null
        );

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

}
