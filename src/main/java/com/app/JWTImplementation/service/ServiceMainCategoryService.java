package com.app.JWTImplementation.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.JWTImplementation.exceptions.ServiceMainCategoryNotFoundException;
import com.app.JWTImplementation.model.ServiceMainCategory;
import com.app.JWTImplementation.repository.ServiceMainCategoryRepository;
import com.app.JWTImplementation.service.impl.IServiceMainCategoryService;

@Service
public class ServiceMainCategoryService implements IServiceMainCategoryService {

    @Autowired
    private ServiceMainCategoryRepository repository;

    @Override
    public List<ServiceMainCategory> findAllServiceMainCategories() {
        return repository.findAll();    
    }

    @Override
    public ServiceMainCategory saveServiceMainCategory(ServiceMainCategory serviceMainCategory) {
        return repository.save(serviceMainCategory);    
    }

    @Override
    public ServiceMainCategory findServiceMainCategoryById(Integer id) {
        return repository.findById(id)
            .orElseThrow(() -> new ServiceMainCategoryNotFoundException(id));    
    }

    @Override
    public void deleteServiceMainCategoryById(Integer id) {
        ServiceMainCategory serviceMainCategory = this.findServiceMainCategoryById(id);
        repository.delete(serviceMainCategory);    
    }

}
