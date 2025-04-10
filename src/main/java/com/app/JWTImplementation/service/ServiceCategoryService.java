package com.app.JWTImplementation.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.JWTImplementation.exceptions.ServiceSubcategoryNotFoundException;
import com.app.JWTImplementation.model.ServiceCategory;
import com.app.JWTImplementation.repository.ServiceCategoryRepository;
import com.app.JWTImplementation.service.impl.IServiceCategoryService;

@Service
public class ServiceCategoryService implements IServiceCategoryService {

    @Autowired
    private ServiceCategoryRepository repository;

    @Override
    public List<ServiceCategory> findAllServiceCategories() {
        return repository.findAll();    
    }

    @Override
    public ServiceCategory saveServiceCategory(ServiceCategory serviceCategory) {
        return repository.save(serviceCategory);
    }

    @Override
    public ServiceCategory findServiceCategoryById(Integer id) {
        return repository.findById(id)
            .orElseThrow(() -> new ServiceSubcategoryNotFoundException(id));    
    }

    @Override
    public void deleteServiceCategoryById(Integer id) {
        ServiceCategory serviceCategory = this.findServiceCategoryById(id);
        repository.delete(serviceCategory);
    }

}
