package com.app.JWTImplementation.service;

import java.util.List;
import java.util.stream.Collectors;

import com.app.JWTImplementation.dto.ServiceSpaDTO;
import com.app.JWTImplementation.dto.projection.ServiceSpaProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.JWTImplementation.dto.ServiceSpaInfoDTO;
import com.app.JWTImplementation.exceptions.ServiceSpaNotFoundException;
import com.app.JWTImplementation.model.ServiceSpa;
import com.app.JWTImplementation.repository.ServiceSpaRepository;
import com.app.JWTImplementation.service.impl.IServiceSpaService;

@Service
public class ServiceSpaService implements IServiceSpaService {

    @Autowired
    private ServiceSpaRepository repository;

    @Override
    public List<ServiceSpa> findAllServicesSpa() {
        return repository.findAll();
    }

    @Override
    public ServiceSpa saveServiceSpa(ServiceSpa serviceSpa) {
        return repository.save(serviceSpa);
    }

    @Override
    public ServiceSpa findServiceSpaById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ServiceSpaNotFoundException(id));
    }

    @Override
    public ServiceSpa updateServiceSpa(Integer id, ServiceSpaDTO serviceSpaDetails) {

        ServiceSpa serviceSpa = this.findServiceSpaById(id);

        serviceSpa.setName(serviceSpaDetails.getName());
        serviceSpa.setDescription(serviceSpaDetails.getDescription());
        serviceSpa.setDurationMinutes(serviceSpaDetails.getDurationMinutes());
        serviceSpa.setIsActive(serviceSpaDetails.getIsActive());

        return repository.save(serviceSpa);

    }

    @Override
    @Transactional
    public void deleteServiceSpaById(Integer id) {
        ServiceSpa serviceSpa = this.findServiceSpaById(id);
        repository.delete(serviceSpa);
    }

    // Metodos sacados desde el repositorio
    public List<ServiceSpaInfoDTO> findAllServicesWhitEntities() {

        List<ServiceSpaProjection> servicesProjections = repository.findAllServiceSpaWhitEntities();

        List<ServiceSpaInfoDTO> servicesDTO = servicesProjections.stream()
                .map(service -> {
                    ServiceSpaInfoDTO dto = ServiceSpaInfoDTO.builder()
                            .id(service.getId())
                            .name(service.getName())
                            .description(service.getDescription())
                            .durationMinutes(service.getDurationMinutes())
                            .isActive(service.getIsActive())
                            .category(service.getCategoryName())
                            .type(service.getIsGroup() ? "Grupal" : "individual")
                            .build();

                    return dto;

                }).collect(Collectors.toList());

        return servicesDTO;

    }

    public ServiceSpaInfoDTO findServiceWithEntityById(Integer id) {

        ServiceSpaProjection service = repository.findServiceSpaWhitEntity(id)
                .orElseThrow(() -> new ServiceSpaNotFoundException(id));

        ServiceSpaInfoDTO dto = ServiceSpaInfoDTO.builder()
                .id(service.getId())
                .name(service.getName())
                .description(service.getDescription())
                .durationMinutes(service.getDurationMinutes())
                .isActive(service.getIsActive())
                .category(service.getCategoryName())
                .type(service.getIsGroup() ? "Grupal" : "individual")
                .build();

        return dto;

    }

}
