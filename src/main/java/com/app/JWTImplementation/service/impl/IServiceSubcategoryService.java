package com.app.JWTImplementation.service.impl;

import java.util.List;

import com.app.JWTImplementation.model.ServiceSubcategory;

public interface IServiceSubcategoryService {
    
    public List<ServiceSubcategory> findAllServiceSubcategories();
    public ServiceSubcategory saveSubcategory(ServiceSubcategory serviceSubcategory);
    public ServiceSubcategory findServiceSubcategoryById(Integer id);
    public void deleteServiceSubcategoryById(Integer id);

}
