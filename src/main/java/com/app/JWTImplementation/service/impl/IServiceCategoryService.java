package com.app.JWTImplementation.service.impl;

import java.util.List;

import com.app.JWTImplementation.model.ServiceCategory;

public interface IServiceCategoryService {
    
    public List<ServiceCategory> findAllServiceCategories();
    public ServiceCategory saveServiceCategory(ServiceCategory serviceCategory);
    public ServiceCategory findServiceCategoryById(Integer id);
    public void deleteServiceCategoryById(Integer id);

}
