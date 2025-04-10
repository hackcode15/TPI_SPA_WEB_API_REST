package com.app.JWTImplementation.service.impl;

import java.util.List;

import com.app.JWTImplementation.dto.ServiceSpaDTO;
import com.app.JWTImplementation.model.ServiceSpa;

public interface IServiceSpaService {
    
    public List<ServiceSpa> findAllServicesSpa();
    public ServiceSpa saveServiceSpa(ServiceSpa serviceSpa);
    public ServiceSpa findServiceSpaById(Integer id);
    public ServiceSpa updateServiceSpa(Integer id, ServiceSpaDTO serviceSpaDetails);
    public void deleteServiceSpaById(Integer id);

}
