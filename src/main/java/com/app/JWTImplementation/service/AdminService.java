package com.app.JWTImplementation.service;

import com.app.JWTImplementation.dto.admin.SaveNewUserDTO;
import com.app.JWTImplementation.dto.projection.TotalIncomeByProfessionalProjection;
import com.app.JWTImplementation.dto.responses.ReservesByProfessional;
import com.app.JWTImplementation.dto.responses.TotalIncomeByProfessional;
import com.app.JWTImplementation.dto.responses.TotalIncomeHistory;
import com.app.JWTImplementation.exceptions.ProfessionalNotFoundException;
import com.app.JWTImplementation.model.Customer;
import com.app.JWTImplementation.model.Professional;
import com.app.JWTImplementation.model.User;
import com.app.JWTImplementation.repository.CustomerRepository;
import com.app.JWTImplementation.repository.InvoiceRepository;
import com.app.JWTImplementation.repository.ProfessionalRepository;
import com.app.JWTImplementation.service.impl.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
public class AdminService implements IAdminService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProfessionalRepository professionalRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

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

    public TotalIncomeHistory getTotalIncomeHistory(LocalDate startDate, LocalDate endDate) {

        BigDecimal total = invoiceRepository.getTotalIncome(startDate, endDate);

        return TotalIncomeHistory.builder()
                .totalPrice(total)
                .startDate(startDate)
                .endDate(endDate)
                .build();

    }

    public TotalIncomeByProfessional getTotalIncomeByProfessional(
            Integer idProfessional,
            LocalDate startDate,
            LocalDate endDate
    ) {

        Professional professional = professionalRepository.findById(idProfessional)
                .orElseThrow(() -> new ProfessionalNotFoundException(idProfessional));

        TotalIncomeByProfessional response = new TotalIncomeByProfessional();

        response.setIdProfessional(professional.getId());
        response.setNameProfessional(professional.getFirstName() + ", " + professional.getLastName());
        response.setSpecialtyProfessional(professional.getSpecialty());

        List<TotalIncomeByProfessionalProjection> reservesResponse = invoiceRepository.getTotalIncomeByProfessional(idProfessional, startDate, endDate);

        Integer countReservation = reservesResponse.size();
        BigDecimal totalPrice = reservesResponse.stream()
                .map(TotalIncomeByProfessionalProjection::getPrice)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        response.setCount(countReservation);
        response.setTotalPrice(totalPrice);

        List<ReservesByProfessional> reserves = reservesResponse.stream()
                .map(p -> {
                    return ReservesByProfessional.builder()
                            .idReserve(p.getIdReserve())
                            .dateReserve(p.getDateReserve().toLocalDate())
                            .timeReserve(p.getDateReserve().toLocalTime())
                            .priceReserve(p.getPrice())
                            .statusReserve(p.getStatusReserve())
                            .nameCustomer(p.getCustomerFullName())
                            .dateTurn(p.getDateTurn().toLocalDate())
                            .timeTurn(p.getDateTurn().toLocalTime())
                            .nameService(p.getServiceName())
                            .build();
                })
                .toList();

        response.setReserves(reserves);

        return response;

    }

}
