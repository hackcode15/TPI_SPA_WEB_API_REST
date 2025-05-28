package com.app.JWTImplementation.service;

import com.app.JWTImplementation.dto.admin.SaveNewUserDTO;
import com.app.JWTImplementation.model.Customer;
import com.app.JWTImplementation.model.Professional;
import com.app.JWTImplementation.model.User;
import com.app.JWTImplementation.repository.CustomerRepository;
import com.app.JWTImplementation.repository.ProfessionalRepository;
import com.app.JWTImplementation.service.impl.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminService implements IAdminService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProfessionalRepository professionalRepository;

    @Override
    @Transactional
    public String creationNewUser(SaveNewUserDTO userDetails) {

        String role = userDetails.getRole().toUpperCase();

        if (!"CUSTOMER".equals(role) && !"PROFESSIONAL".equals(role)) {
            throw new RuntimeException("No puedes crear otros tipos de usuarios distintos de customer y proffesional");
        }

        if ("CUSTOMER".equals(role)) {
            Customer customer = Customer.builder()
                    .email(userDetails.getEmail())
                    .username(userDetails.getUsername())
                    .password(passwordEncoder.encode(userDetails.getPassword()))
                    .firstName(userDetails.getFirstName())
                    .lastName(userDetails.getLastName())
                    .phone(userDetails.getPhone())
                    .birthdate(userDetails.getBirthdate())
                    .role(User.Role.CUSTOMER)
                    .build();
            customerRepository.save(customer);
            return "Cliente cargado exitosamente";
        } else if ("PROFESSIONAL".equals(role)) {
            Professional professional = Professional.builder()
                    .email(userDetails.getEmail())
                    .username(userDetails.getUsername())
                    .password(passwordEncoder.encode(userDetails.getPassword()))
                    .firstName(userDetails.getFirstName())
                    .lastName(userDetails.getLastName())
                    .specialty(userDetails.getSpecialty())
                    .license(userDetails.getLicense())
                    .photoUrl(userDetails.getPhotoUrl())
                    .role(User.Role.PROFESSIONAL)
                    .build();
            professionalRepository.save(professional);
            return "Profesional cargado exitosamente";
        }

        //return "Error al cargar un nuevo usuario";
        throw new IllegalStateException("Flujo inesperado en creación de usuario");

    }

}
