package com.app.JWTImplementation.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.JWTImplementation.dto.ServiceSpaDTO;
import com.app.JWTImplementation.exceptions.ServiceSpaNotFoundException;
import com.app.JWTImplementation.model.ServiceSpa;
import com.app.JWTImplementation.repository.ServiceSpaRepository;
import com.app.JWTImplementation.service.impl.IServiceSpaService;

@Service
public class ServiceSpaService implements IServiceSpaService {

    @Autowired
    private ServiceSpaRepository repository;

    @Override
    public List<ServiceSpa> findAllServiceSpa() {
        return repository.findAll();    
    }

    @Override
    public ServiceSpa saveServiceSpa(ServiceSpa serviceSpa) {
        return repository.save(serviceSpa);    
    }

    @Override
    public ServiceSpa updateServiceSpaById(Integer id, ServiceSpaDTO serviceDtoDetails) {
    
        ServiceSpa serviceSpa = this.findServiceSpaById(id);

        serviceSpa.setName(serviceDtoDetails.getName());
        serviceSpa.setDescription(serviceDtoDetails.getDescription());
        serviceSpa.setStatus(serviceDtoDetails.getStatus_name());

        return this.saveServiceSpa(serviceSpa);
    
    }

    @Override
    public ServiceSpa findServiceSpaById(Integer id) {
        return repository.findById(id)
            .orElseThrow(() -> new ServiceSpaNotFoundException(id));
    }

    @Override
    public void deleteServiceSpaById(Integer id) {
        ServiceSpa serviceSpa = this.findServiceSpaById(id);
        repository.delete(serviceSpa);    
    }
    
}
