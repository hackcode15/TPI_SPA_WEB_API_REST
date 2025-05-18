package com.app.JWTImplementation.service.impl;

import com.app.JWTImplementation.dto.customer.CustomerRequestDTO;
import com.app.JWTImplementation.dto.customer.CustomerResponseDTO;

public interface ICustomerService {
    public CustomerResponseDTO updateCustomer(Integer id, CustomerRequestDTO userDetails);
}
