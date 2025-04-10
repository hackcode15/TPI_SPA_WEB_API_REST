package com.app.JWTImplementation.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.JWTImplementation.dto.ServiceSubcategoryDTO;
import com.app.JWTImplementation.dto.responses.ApiResponse;
import com.app.JWTImplementation.model.ServiceSubcategory;
import com.app.JWTImplementation.service.ServiceSubcategoryService;

@RestController
@RequestMapping("/api/service-subcategory")
public class ServiceSubcategoryController {
    
    @Autowired private ServiceSubcategoryService service;

    @GetMapping
    @ResponseBody
    public ResponseEntity<ApiResponse<List<ServiceSubcategoryDTO>>> getAllServiceSubcategories() {
        
        List<ServiceSubcategory> serviceSubcategories = service.findAllServiceSubcategories();

        List<ServiceSubcategoryDTO> serviceSubcategoryDTO = serviceSubcategories.stream()
            .map(serviceSubcategory -> {
                
                ServiceSubcategoryDTO dto = ServiceSubcategoryDTO.builder()
                    .id(serviceSubcategory.getId())
                    .name(serviceSubcategory.getName())
                    .build();

                return dto;

            }).collect(Collectors.toList());

        ApiResponse<List<ServiceSubcategoryDTO>> response = new ApiResponse<>(
            "Success",
            "Servicies Subcategories retrived succesfully",
            serviceSubcategoryDTO
        );

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<ApiResponse<ServiceSubcategoryDTO>> getServiceSubcategory(@PathVariable("id") Integer id) {
        
        ServiceSubcategory serviceSubcategory = service.findServiceSubcategoryById(id);

        ServiceSubcategoryDTO serviceSubcategoryDTO = ServiceSubcategoryDTO.builder()
            .id(serviceSubcategory.getId())
            .name(serviceSubcategory.getName())
            .build();

        ApiResponse<ServiceSubcategoryDTO> response = new ApiResponse<>(
            "Success",
            "Service Subcategory successfully recovered",
            serviceSubcategoryDTO
        );

        return new ResponseEntity<>(response, HttpStatus.OK);

    }
    
}
