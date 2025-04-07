package com.app.JWTImplementation.service.impl;

import java.util.List;

import com.app.JWTImplementation.model.ServiceMainCategory;

public interface IServiceMainCategoryService {
    
    public List<ServiceMainCategory> findAllServiceMainCategories();
    public ServiceMainCategory saveServiceMainCategory(ServiceMainCategory serviceMainCategory);
    public ServiceMainCategory findServiceMainCategoryById(Integer id);
    public void deleteServiceMainCategoryById(Integer id);

}
