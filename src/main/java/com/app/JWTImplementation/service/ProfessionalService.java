package com.app.JWTImplementation.service;

import com.app.JWTImplementation.dto.ReserveInfoDTO;
import com.app.JWTImplementation.dto.professional.ProfessionalRequestDTO;
import com.app.JWTImplementation.dto.professional.ProfessionalResponseDTO;
import com.app.JWTImplementation.dto.projection.ProfessionalProjection;
import com.app.JWTImplementation.dto.projection.ReserveProjection;
import com.app.JWTImplementation.exceptions.ProfessionalNotFoundException;
import com.app.JWTImplementation.model.Professional;
import com.app.JWTImplementation.model.User;
import com.app.JWTImplementation.repository.ProfessionalRepository;
import com.app.JWTImplementation.service.impl.IProfessionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfessionalService implements IProfessionalService {

    @Autowired
    private ProfessionalRepository professionalRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /*@Override
    public List<Professional> findAllProfessional() {
        return null;
    }

    @Override
    public Professional findProfessionalById(Integer id) {
        return null;
    }

    @Override
    public Professional saveProfessional(Professional professional) {
        return null;
    }*/

    @Override
    public void deleteProfessionalById(Integer id) {

        Professional professional = professionalRepository.findById(id)
                .orElseThrow(() -> new ProfessionalNotFoundException(id));

        professionalRepository.delete(professional);

    }

    @Override
    public ProfessionalResponseDTO updateProfessionalById(Integer id, ProfessionalRequestDTO professionalDetails) {

        Professional professional = professionalRepository.findById(id)
                .orElseThrow(() -> new ProfessionalNotFoundException(id));

        if (professionalDetails.getPassword() == null || professionalDetails.getPassword().isEmpty()) {
            throw new RuntimeException("La contraseña es obligatoria");
        }

        professional.setEmail(professionalDetails.getEmail());
        professional.setUsername(professionalDetails.getUsername());
        professional.setPassword(passwordEncoder.encode(professionalDetails.getPassword()));
        professional.setFirstName(professionalDetails.getFirstName());
        professional.setLastName(professionalDetails.getLastName());
        professional.setSpecialty(professionalDetails.getSpecialty());
        professional.setLicense(professionalDetails.getLicense());
        professional.setPhotoUrl(professionalDetails.getPhotoUrl());
        professional.setRole(User.Role.PROFESSIONAL);

        professionalRepository.save(professional);

        return ProfessionalResponseDTO.builder()
                .id(professional.getId())
                .email(professional.getEmail())
                .username(professional.getUsername())
                .password(professional.getPassword())
                .firstName(professional.getFirstName())
                .lastName(professional.getLastName())
                .specialty(professional.getSpecialty())
                .license(professional.getLicense())
                .photoUrl(professional.getPhotoUrl())
                .build();

    }


    public List<ProfessionalResponseDTO> getAllProfessionals() {

        List<ProfessionalProjection> professionalProjections = professionalRepository.findAllProfessionals();

        return professionalProjections.stream()
                .map(professional -> {

                    return ProfessionalResponseDTO.builder()
                            .id(professional.getId())
                            .email(professional.getEmail())
                            .username(professional.getUsername())
                            .password(professional.getPassword())
                            .firstName(professional.getFirstName())
                            .lastName(professional.getLastName())
                            .specialty(professional.getSpecialty())
                            .license(professional.getLicense())
                            .photoUrl(professional.getPhotoUrl())
                            .role(professional.getRole())
                            .build();

                }).toList();

    }

    public ProfessionalResponseDTO getProfessionalById(Integer id) {

        ProfessionalProjection professional = professionalRepository.findProfessionalById(id)
                .orElseThrow(() -> new ProfessionalNotFoundException(id));

        return ProfessionalResponseDTO.builder()
                .id(professional.getId())
                .email(professional.getEmail())
                .username(professional.getUsername())
                .password(professional.getPassword())
                .firstName(professional.getFirstName())
                .lastName(professional.getLastName())
                .specialty(professional.getSpecialty())
                .license(professional.getLicense())
                .photoUrl(professional.getPhotoUrl())
                .role(professional.getRole())
                .build();

    }

    public List<ReserveInfoDTO> viewMyAssignedReservations(Integer id) {

        List<ReserveProjection> reservations = professionalRepository.myAssignedReservations(id);

        return reservations.stream()
                .map(reserve -> {
                    return ReserveInfoDTO.builder()
                            .id(reserve.getId())
                            .dateReserve(reserve.getDateReserve().toLocalDate())
                            .userFullName(reserve.getUserFullName())
                            .serviceName(reserve.getServiceName())
                            .servicePrice(reserve.getServicePrice())
                            .startDate(reserve.getScheduleStart().toLocalDate())
                            .startTime(reserve.getScheduleStart().toLocalTime())
                            .endTime(reserve.getScheduleEnd().toLocalTime())
                            .professionalName(reserve.getProfessionalName())
                            .status(reserve.getStatus())
                            .build();
                }).toList();

    }

}
