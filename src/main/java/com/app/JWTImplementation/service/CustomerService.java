package com.app.JWTImplementation.service;

import com.app.JWTImplementation.dto.customer.CustomerRequestDTO;
import com.app.JWTImplementation.dto.customer.CustomerResponseDTO;
import com.app.JWTImplementation.dto.projection.CustomerProjection;
import com.app.JWTImplementation.exceptions.CustomerNotFounException;
import com.app.JWTImplementation.model.Customer;
import com.app.JWTImplementation.model.User;
import com.app.JWTImplementation.repository.CustomerRepository;
import com.app.JWTImplementation.service.impl.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class CustomerService implements ICustomerService {

    @Autowired
    private CustomerRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<CustomerResponseDTO> findAllCustomers() {

        List<CustomerProjection> customerProjections = repository.findAllCustomers();

        List<CustomerResponseDTO> customers = customerProjections.stream()
                .map(customer -> {
                    CustomerResponseDTO dto = CustomerResponseDTO.builder()
                            .id(customer.getId())
                            .email(customer.getEmail())
                            .username(customer.getUsername())
                            .password(customer.getPassword())
                            .firstName(customer.getFirstName())
                            .lastName(customer.getLastName())
                            .phone(customer.getPhone())
                            .birthdate(customer.getBirthdate())
                            .role(customer.getRole())
                            .build();
                    return dto;
                }).toList();

        return customers;

    }

    public CustomerResponseDTO findCustomerById(Integer id) {

        CustomerProjection customer = repository.findCustomerById(id)
                .orElseThrow(() -> new CustomerNotFounException(id));

        CustomerResponseDTO customerDto = CustomerResponseDTO.builder()
                .id(customer.getId())
                .email(customer.getEmail())
                .username(customer.getUsername())
                .password(customer.getPassword())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .phone(customer.getPhone())
                .birthdate(customer.getBirthdate())
                .role(customer.getRole())
                .build();

        return customerDto;

    }

    @Override
    @Transactional
    public CustomerResponseDTO updateCustomer(Integer id, CustomerRequestDTO userDetails) {

        Customer customer = repository.findById(id)
                .orElseThrow(() -> new CustomerNotFounException(id));

        if (userDetails.getPassword() == null || userDetails.getPassword().isEmpty()) {
            throw new RuntimeException("La contraseña es obligatoria");
        }

        customer.setEmail(userDetails.getEmail());
        customer.setUsername(userDetails.getUsername());
        customer.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        customer.setFirstName(userDetails.getFirstName());
        customer.setLastName(userDetails.getLastName());
        customer.setPhone(userDetails.getPhone());
        customer.setBirthdate(userDetails.getBirthdate());
        customer.setRole(User.Role.CUSTOMER);

        repository.save(customer);

        return new CustomerResponseDTO(
                customer.getId(),
                customer.getEmail(),
                customer.getUsername(),
                customer.getPassword(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getPhone(),
                customer.getBirthdate(),
                customer.getRole().name()
        );

    }


}
