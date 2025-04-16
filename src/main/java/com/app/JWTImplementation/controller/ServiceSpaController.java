package com.app.JWTImplementation.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.app.JWTImplementation.dto.ServiceSpaDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.app.JWTImplementation.dto.ServiceSpaInfoDTO;
import com.app.JWTImplementation.dto.responses.ApiResponse;
import com.app.JWTImplementation.model.ServiceSpa;
import com.app.JWTImplementation.service.ServiceSpaService;

@RestController
@RequestMapping("/api/service-spa")
@Tag(name = "Servicio", description = "Controlador para los Servicios")
public class ServiceSpaController {

    @Autowired
    private ServiceSpaService service;

    @GetMapping("/list") // /list-info
    @ResponseBody
    @Operation(
            summary = "Ver todos los Servicios con su información completa",
            description = "Lista todos los servicios del Spa con toda su informacion y la de sus relaciones",
            tags = {"Servicio"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Servicio Spa Toda la información recuperada correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ServiceSpaInfoDTO.class
                                    )
                            )
                    )
            }
    )
    public ResponseEntity<ApiResponse<List<ServiceSpaInfoDTO>>> getAllServicesSpaInfo() {

        List<ServiceSpaInfoDTO> servicesSpa = service.findAllServicesWhitEntities();

        ApiResponse<List<ServiceSpaInfoDTO>> response = new ApiResponse<>(
                "Success",
                "Servicies Spa All Info retrived succesfully",
                servicesSpa
        );

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @GetMapping("/{id}") // /info/{id}
    @ResponseBody
    @Operation(
            summary = "Mostrar información completa del servicio por su ID",
            description = "Busca el Servicio por su ID y lo muestra con toda su informacion y la de sus relaciones",
            tags = {"Servicio"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ServiceSpaInfoDTO.class
                                    )
                            )
                    )
            }
    )
    public ResponseEntity<ApiResponse<ServiceSpaInfoDTO>> getServiceSpa(@PathVariable("id") Integer id) {

        ServiceSpaInfoDTO serviceSpaInfoDTO = service.findServiceWithEntityById(id);

        ApiResponse<ServiceSpaInfoDTO> response = new ApiResponse<>(
                "Success",
                "Servicio Spa Toda la información recuperada correctamente",
                serviceSpaInfoDTO
        );

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    /*
    @GetMapping("/list")
    @ResponseBody
    @Operation(
            summary = "Ver todos los Servicios",
            description = "Lista todos los servicios (solo su información)",
            tags = {"Servicio"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Servicios Spa recuperados con éxito",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ServiceSpaDTO.class
                                    )
                            )
                    )
            }
    )
    public ResponseEntity<ApiResponse<List<ServiceSpaDTO>>> getAllServicesSpa() {

        List<ServiceSpa> servicesSpa = service.findAllServicesSpa();

        List<ServiceSpaDTO> servicesSpaDTO = servicesSpa.stream()
                .map(serviceSpa -> {

                    ServiceSpaDTO dto = ServiceSpaDTO.builder()
                            .id(serviceSpa.getId())
                            .name(serviceSpa.getName())
                            .description(serviceSpa.getDescription())
                            .category(serviceSpa.getCategoryName())
                            .durationMinutes(serviceSpa.getDurationMinutes())
                            .isActive(serviceSpa.getIsActive())
                            .isGroupService(serviceSpa.getIsGroupService())
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
     */

    /*
    @GetMapping("/{id}")
    @ResponseBody
    @Operation(
            summary = "Mostrar Servicio por su ID",
            description = "Busca el Servicio por su ID y muestra solo su información",
            tags = {"Servicio"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "El Service Spa se recuperó con éxito",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ServiceSpaDTO.class
                                    )
                            )
                    )
            }
    )
    public ResponseEntity<ApiResponse<ServiceSpaDTO>> getServiceSpaById(@PathVariable("id") Integer id) {

        ServiceSpa serviceSpa = service.findServiceSpaById(id);

        ServiceSpaDTO serviceSpaDTO = ServiceSpaDTO.builder()
                .id(serviceSpa.getId())
                .name(serviceSpa.getName())
                .description(serviceSpa.getDescription())
                .category(serviceSpa.getCategoryName())
                .durationMinutes(serviceSpa.getDurationMinutes())
                .isActive(serviceSpa.getIsActive())
                .isGroupService(serviceSpa.getIsGroupService())
                .build();

        ApiResponse<ServiceSpaDTO> response = new ApiResponse<>(
                "Success",
                "Service Spa successfully recovered",
                serviceSpaDTO
        );

        return new ResponseEntity<>(response, HttpStatus.OK);

    }
    */

    // post
    @PostMapping("/new")
    @ResponseBody
    @Operation(
            summary = "Nuevo Servicio",
            description = "Creación de un nuevo servicio",
            tags = {"Servicio"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Solicitud de creacion el nombre, descripción, categoria, la duración en minutos, si esta activo y si es de tipo grupal o individual",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = ServiceSpaDTO.class
                            )
                    )
            ),
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "201",
                            description = "Servicio Spa creado con éxito",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ServiceSpaInfoDTO.class
                                    )
                            )
                    )
            }
    )
    public ResponseEntity<ApiResponse<ServiceSpaInfoDTO>> newServiceSpa(@RequestBody ServiceSpaDTO serviceSpaDetails) {

        ServiceSpa createdServiceSpa = ServiceSpa.builder()
                .name(serviceSpaDetails.getName())
                .description(serviceSpaDetails.getDescription())
                .categoryName(serviceSpaDetails.getCategory())
                .durationMinutes(serviceSpaDetails.getDurationMinutes())
                .isActive(serviceSpaDetails.getIsActive())
                .isGroupService(serviceSpaDetails.getIsGroupService())
                .build();

        ServiceSpa newServiceSpa = service.saveServiceSpa(createdServiceSpa);

        ServiceSpaInfoDTO serviceSpaInfoDTO = service.findServiceWithEntityById(newServiceSpa.getId());

        ApiResponse<ServiceSpaInfoDTO> response = new ApiResponse<>(
                "Success",
                "Service Spa created successfully",
                serviceSpaInfoDTO
        );

        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    @PutMapping("/update/{id}")
    @ResponseBody
    @Operation(
            summary = "Editar un Servicio por su ID",
            description = "Actualiza todos los datos del Servicio por su ID si existe",
            tags = {"Servicio"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Solicitidud de modificación con el id, nombre, descripción, categoria, la duración en minutos, si esta activo y si es de tipo grupal o individual ",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = ServiceSpaDTO.class
                            )
                    )
            ),
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "201",
                            description = "Servicio Spa actualizado con éxito",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ServiceSpaInfoDTO.class
                                    )
                            )
                    )
            }
    )
    public ResponseEntity<ApiResponse<ServiceSpaInfoDTO>> updateServiceSpa(
            @PathVariable("id") Integer id,
            @RequestBody ServiceSpaDTO serviceSpaDetails) {

        ServiceSpa serviceSpaUpdate = service.updateServiceSpa(id, serviceSpaDetails);

        ServiceSpaInfoDTO serviceSpaInfoDTO = ServiceSpaInfoDTO.builder()
                .id(serviceSpaUpdate.getId())
                .name(serviceSpaUpdate.getName())
                .description(serviceSpaUpdate.getDescription())
                .category(serviceSpaUpdate.getCategoryName())
                .durationMinutes(serviceSpaUpdate.getDurationMinutes())
                .isActive(serviceSpaUpdate.getIsActive())
                .type(serviceSpaUpdate.getIsGroupService() ? "Grupal" : "individual")
                .build();

        ApiResponse<ServiceSpaInfoDTO> response = new ApiResponse<>(
                "Success",
                "Updated service spa successfully",
                serviceSpaInfoDTO
        );

        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    @DeleteMapping("/delete/{id}")
    @Operation(
            summary = "Borrar Servicio",
            description = "Elimina el Servicio por ID",
            tags = {"Servicio"},
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Servicio Spa eliminado con éxito",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ApiResponse.class
                                    )
                            )
                    )
            }
    )
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
