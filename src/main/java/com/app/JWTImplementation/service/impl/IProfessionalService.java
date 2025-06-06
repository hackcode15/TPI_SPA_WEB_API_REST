package com.app.JWTImplementation.service.impl;

import com.app.JWTImplementation.dto.professional.ProfessionalRequestDTO;
import com.app.JWTImplementation.dto.professional.ProfessionalResponseDTO;
import com.app.JWTImplementation.model.Professional;

import java.util.List;

public interface IProfessionalService {
    //public List<Professional> findAllProfessional();
    //public Professional findProfessionalById(Integer id);
    //public Professional saveProfessional(Professional professional);
    public void deleteProfessionalById(Integer id);
    public ProfessionalResponseDTO updateProfessionalById(Integer id, ProfessionalRequestDTO professionalDetails);
}
