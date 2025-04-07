package com.app.JWTImplementation.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.JWTImplementation.exceptions.ServiceSubcategoryNotFoundException;
import com.app.JWTImplementation.model.ServiceSubcategory;
import com.app.JWTImplementation.repository.ServiceSubcategoryRepository;
import com.app.JWTImplementation.service.impl.IServiceSubcategoryService;

@Service
public class ServiceSubcategoryService implements IServiceSubcategoryService {

    @Autowired
    private ServiceSubcategoryRepository repository;

    @Override
    public List<ServiceSubcategory> findAllServiceSubcategories() {
        return repository.findAll();    
    }

    @Override
    public ServiceSubcategory saveSubcategory(ServiceSubcategory serviceSubcategory) {
        return repository.save(serviceSubcategory);    
    }

    @Override
    public ServiceSubcategory findServiceSubcategoryById(Integer id) {
        return repository.findById(id)
            .orElseThrow(() -> new ServiceSubcategoryNotFoundException(id));    
    }

    @Override
    public void deleteServiceSubcategoryById(Integer id) {
        ServiceSubcategory serviceSubcategory = this.findServiceSubcategoryById(id);
        repository.delete(serviceSubcategory);    
    }

}
