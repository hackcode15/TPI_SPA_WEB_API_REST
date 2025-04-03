package com.app.JWTImplementation.service.impl;

import java.util.List;

import com.app.JWTImplementation.dto.ServiceSpaDTO;
import com.app.JWTImplementation.model.ServiceSpa;

public interface IServiceSpaService {
    
    public List<ServiceSpa> findAllServiceSpa();
    public ServiceSpa saveServiceSpa(ServiceSpa serviceSpa);

    public ServiceSpa updateServiceSpaById(Integer id, ServiceSpaDTO serviceDtoDetails);

    public ServiceSpa findServiceSpaById(Integer id);
    public void deleteServiceSpaById(Integer id);

}
