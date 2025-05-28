package com.app.JWTImplementation.controller;

import com.app.JWTImplementation.dto.ReserveInfoDTO;
import com.app.JWTImplementation.dto.professional.ProfessionalRequestDTO;
import com.app.JWTImplementation.dto.professional.ProfessionalResponseDTO;
import com.app.JWTImplementation.dto.responses.ApiResponse;
import com.app.JWTImplementation.model.Professional;
import com.app.JWTImplementation.service.ProfessionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/professional")
public class ProfessionalController {

    @Autowired
    private ProfessionalService professionalService;

    @GetMapping("/list")
    @ResponseBody
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<ProfessionalResponseDTO>>> getAllProfessional(){
        ApiResponse<List<ProfessionalResponseDTO>> response = new ApiResponse<>(
                "Success",
                "Professionals retrived successfully",
                professionalService.getAllProfessionals()
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ResponseBody
    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSIONAL')")
    public ResponseEntity<ApiResponse<ProfessionalResponseDTO>> getProfessional(@PathVariable("id") Integer id){
        ApiResponse<ProfessionalResponseDTO> response = new ApiResponse<>(
                "Success",
                "Professional retrived successfully",
                professionalService.getProfessionalById(id)
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    @ResponseBody
    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSIONAL')")
    public ResponseEntity<ApiResponse<ProfessionalResponseDTO>> updateProfessionalById(
            @PathVariable("id") Integer id,
            @RequestBody ProfessionalRequestDTO professionalDetails
    ) {

        ApiResponse<ProfessionalResponseDTO> response = new ApiResponse<>(
                "Success",
                "Professional updated successfully",
                professionalService.updateProfessionalById(id, professionalDetails)
        );

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<String>> deleteProfessionalById(@PathVariable("id") Integer id) {

        professionalService.deleteProfessionalById(id);

        ApiResponse<String> response = new ApiResponse<>(
                "Success",
                "Professional removed successfully",
                null
        );

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PreAuthorize("hasRole('PROFESSIONAL')")
    @GetMapping("/my-assigned-reservations/{id}")
    @ResponseBody
    public ResponseEntity<ApiResponse<List<ReserveInfoDTO>>> getAllMyAssignedReservations(@PathVariable("id") Integer id) {

        return new ResponseEntity<>(
                new ApiResponse<>(
                        "Succes",
                        "Reservation assigned retrived",
                        professionalService.viewMyAssignedReservations(id)
                ),
                HttpStatus.OK
        );

    }

}











