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

import com.app.JWTImplementation.dto.ServiceCategoryDTO;
import com.app.JWTImplementation.dto.responses.ApiResponse;
import com.app.JWTImplementation.model.ServiceCategory;
import com.app.JWTImplementation.service.ServiceCategoryService;

@RestController
@RequestMapping("/api/service-subcategory")
public class ServiceSubcategoryController {
    
    @Autowired private ServiceCategoryService service;

    @GetMapping
    @ResponseBody
    public ResponseEntity<ApiResponse<List<ServiceCategoryDTO>>> getAllServiceSubcategories() {
        
        List<ServiceCategory> serviceSubcategories = service.findAllServiceCategories();

        List<ServiceCategoryDTO> serviceCategoryDTO = serviceSubcategories.stream()
            .map(serviceSubcategory -> {
                
                ServiceCategoryDTO dto = ServiceCategoryDTO.builder()
                    .id(serviceSubcategory.getId())
                    .name(serviceSubcategory.getName())
                    .build();

                return dto;

            }).collect(Collectors.toList());

        ApiResponse<List<ServiceCategoryDTO>> response = new ApiResponse<>(
            "Success",
            "Servicies Subcategories retrived succesfully",
                serviceCategoryDTO
        );

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<ApiResponse<ServiceCategoryDTO>> getServiceSubcategory(@PathVariable("id") Integer id) {
        
        ServiceCategory serviceCategory = service.findServiceCategoryById(id);

        ServiceCategoryDTO serviceCategoryDTO = ServiceCategoryDTO.builder()
            .id(serviceCategory.getId())
            .name(serviceCategory.getName())
            .build();

        ApiResponse<ServiceCategoryDTO> response = new ApiResponse<>(
            "Success",
            "Service Subcategory successfully recovered",
                serviceCategoryDTO
        );

        return new ResponseEntity<>(response, HttpStatus.OK);

    }
    
}
