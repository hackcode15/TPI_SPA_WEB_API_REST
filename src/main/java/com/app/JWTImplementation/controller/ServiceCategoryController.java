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
@RequestMapping("/api/service-category")
public class ServiceCategoryController {

    @Autowired
    private ServiceCategoryService service;

    @GetMapping("/list")
    @ResponseBody
    public ResponseEntity<ApiResponse<List<ServiceCategoryDTO>>> getAllServiceCategories() {

        List<ServiceCategory> servicesCategories = service.findAllServiceCategories();

        List<ServiceCategoryDTO> serviceCategoryDTO = servicesCategories.stream()
                .map(category -> {

                    ServiceCategoryDTO dto = ServiceCategoryDTO.builder()
                            .id(category.getId())
                            .name(category.getName())
                            .description(category.getDescription())
                            .isGroupService(category.getIsGroupService())
                            .build();

                    return dto;

                }).collect(Collectors.toList());

        ApiResponse<List<ServiceCategoryDTO>> response = new ApiResponse<>(
                "Success",
                "Servicies Categories retrived succesfully",
                serviceCategoryDTO
        );

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<ApiResponse<ServiceCategoryDTO>> getServiceCategory(@PathVariable("id") Integer id) {

        ServiceCategory category = service.findServiceCategoryById(id);

        ServiceCategoryDTO serviceCategoryDTO = ServiceCategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .isGroupService(category.getIsGroupService())
                .build();

        ApiResponse<ServiceCategoryDTO> response = new ApiResponse<>(
                "Success",
                "Service Category successfully recovered",
                serviceCategoryDTO
        );

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

}
