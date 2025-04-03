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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.JWTImplementation.dto.ServiceSpaDTO;
import com.app.JWTImplementation.dto.responses.ApiResponse;
import com.app.JWTImplementation.model.ServiceSpa;
import com.app.JWTImplementation.service.ServiceSpaService;

@RestController
@RequestMapping("/api/service")
public class ServiceSpaController {
    
    @Autowired
    private ServiceSpaService service;

    @GetMapping
    @ResponseBody
    public ResponseEntity<ApiResponse<List<ServiceSpaDTO>>> getAllServiceSpa() {
        
        List<ServiceSpa> serviceSpas = service.findAllServiceSpa();

        List<ServiceSpaDTO> serviceSpaResponse = serviceSpas.stream()
            .map(serviceSpa -> {
                
                ServiceSpaDTO dto = ServiceSpaDTO.builder()
                    .id(serviceSpa.getId())
                    .name(serviceSpa.getName())
                    .description(serviceSpa.getDescription())
                    .status_name(serviceSpa.getStatus())
                    .build();

                return dto;

            }).collect(Collectors.toList());

        ApiResponse<List<ServiceSpaDTO>> response = new ApiResponse<>(
            "Success",
            "Service Spa retrived succesfully",
            serviceSpaResponse
        );

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<ApiResponse<ServiceSpaDTO>> getServiceSpa(@PathVariable("id") Integer id) {
        
        ServiceSpa serviceSpa = service.findServiceSpaById(id);

        ServiceSpaDTO serviceSpaDTO = ServiceSpaDTO.builder()
            .id(serviceSpa.getId())
            .name(serviceSpa.getName())
            .description(serviceSpa.getDescription())
            .status_name(serviceSpa.getStatus())
            .build();

        ApiResponse<ServiceSpaDTO> response = new ApiResponse<>(
            "Success",
            "Service Spa successfully recovered",
            serviceSpaDTO
        );

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PostMapping("/new")
    @ResponseBody
    public ResponseEntity<ApiResponse<ServiceSpaDTO>> newServiceSpa(@RequestBody ServiceSpaDTO serviceSpaDTO) {
        
        ServiceSpa serviceSpa = ServiceSpa.builder()
            .id(serviceSpaDTO.getId())
            .name(serviceSpaDTO.getName())
            .description(serviceSpaDTO.getDescription())
            .status(serviceSpaDTO.getStatus_name())
            .build();

        service.saveServiceSpa(serviceSpa);

        ServiceSpaDTO newServicieSpaCreated = ServiceSpaDTO.builder()
            .id(serviceSpa.getId())
            .name(serviceSpa.getName())
            .description(serviceSpa.getDescription())
            .status_name(serviceSpa.getStatus())
            .build();

        ApiResponse<ServiceSpaDTO> response = new ApiResponse<>(
            "Success",
            "Spa Service Created Successfully",
            newServicieSpaCreated
        );

        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    @PutMapping("/update/{id}")
    @ResponseBody
    public ResponseEntity<ApiResponse<ServiceSpaDTO>> updateServiceSpa(
        @PathVariable("id") Integer id,
        @RequestBody ServiceSpaDTO serviceSpaDetails) {
    
        ServiceSpa serviceSpa = service.updateServiceSpaById(id, serviceSpaDetails);

        ServiceSpaDTO serviceSpaDTO = ServiceSpaDTO.builder()
            .id(serviceSpa.getId())
            .name(serviceSpa.getName())
            .description(serviceSpa.getDescription())
            .status_name(serviceSpa.getStatus())
            .build();

        ApiResponse<ServiceSpaDTO> response = new ApiResponse<>(
            "Success",
            "Updated Service Spa successfully",
            serviceSpaDTO
        );

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<String>> deleteServiceSpa(@PathVariable("id") Integer id){
        
        service.deleteServiceSpaById(id);

        ApiResponse<String> response = new ApiResponse<>(
            "Success",
            "Deleted Service Spa successfully",
            null
        );

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

}
