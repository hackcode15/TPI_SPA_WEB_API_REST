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

import com.app.JWTImplementation.dto.ServiceMainCategoryDTO;
import com.app.JWTImplementation.dto.responses.ApiResponse;
import com.app.JWTImplementation.model.ServiceMainCategory;
import com.app.JWTImplementation.service.ServiceMainCategoryService;

@RestController
@RequestMapping("/api/service-main-category")
public class ServiceMainCategoryController {
    
    @Autowired private ServiceMainCategoryService service;

    @GetMapping
    @ResponseBody
    public ResponseEntity<ApiResponse<List<ServiceMainCategoryDTO>>> getAllServicesMainCategories() {
        
        List<ServiceMainCategory> serviceMainCategories = service.findAllServiceMainCategories();

        List<ServiceMainCategoryDTO> serviceMainCategoryDTOs = serviceMainCategories.stream()
            .map(serviceMainCategory -> {
                
                ServiceMainCategoryDTO dto = ServiceMainCategoryDTO.builder()
                    .id(serviceMainCategory.getId())
                    .name(serviceMainCategory.getName())
                    .description(serviceMainCategory.getDescription())
                    .isGroupService(serviceMainCategory.getIsGroupService())
                    .build();

                return dto;

            }).collect(Collectors.toList());

        ApiResponse<List<ServiceMainCategoryDTO>> response = new ApiResponse<>(
            "Success",
            "Services Main Category retrived succesfully",
            serviceMainCategoryDTOs
        );

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<ApiResponse<ServiceMainCategoryDTO>> getServiceMainCategory (@PathVariable("id") Integer id) {
        
        ServiceMainCategory serviceMainCategory = service.findServiceMainCategoryById(id);

        ServiceMainCategoryDTO serviceMainCategoryDTO = ServiceMainCategoryDTO.builder()
            .id(serviceMainCategory.getId())
            .name(serviceMainCategory.getName())
            .description(serviceMainCategory.getDescription())
            .isGroupService(serviceMainCategory.getIsGroupService())
            .build();

        ApiResponse<ServiceMainCategoryDTO> response = new ApiResponse<>(
            "Success",
            "Service Main Category successfully recovered",
            serviceMainCategoryDTO
        );

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

}
