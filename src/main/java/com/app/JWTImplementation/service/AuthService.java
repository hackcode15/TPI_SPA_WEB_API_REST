package com.app.JWTImplementation.service;

import java.time.LocalDateTime;

import com.app.JWTImplementation.dto.EmailRegisterDTO;
import com.app.JWTImplementation.exceptions.UserNotFoundException;
import com.app.JWTImplementation.model.Customer;
import com.app.JWTImplementation.repository.CustomerRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.JWTImplementation.JWT.JwtService;
import com.app.JWTImplementation.dto.request.LoginRequest;
import com.app.JWTImplementation.dto.request.RegisterRequest;
import com.app.JWTImplementation.dto.responses.AuthResponse;
import com.app.JWTImplementation.model.User;
import com.app.JWTImplementation.model.User.Role;
import com.app.JWTImplementation.repository.UserRepository;
import com.app.JWTImplementation.service.impl.IAuthService;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService implements IAuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private EmailService emailService;

    @Autowired
    private TaskExecutor taskExecutor;

    @Override
    public AuthResponse login(LoginRequest request) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        //UserDetails user = userRepository.findUserByUsername(request.getUsername()).orElseThrow();

        //String token = jwtService.getToken(user);

        // Podria utilizar solo el UserDetails para obtener el username
        // Otro paso mas para obtener el id y username
        User userLogin = userRepository.findUserByUsername(request.getUsername())
                .orElseThrow(() -> new UserNotFoundException("User not foud whit username: " + request.getUsername()));

        String token = jwtService.getToken(userLogin, userLogin.getId());

        return AuthResponse.builder()
                .status("Success")
                .message("You have successfully logged in")
                .idUser(userLogin.getId())
                .email(userLogin.getEmail())
                .username(userLogin.getUsername())
                .rol(userLogin.getRole().name())
                .token(token)
                .build();

    }

    @Override
    @Transactional
    public AuthResponse register(RegisterRequest request) throws MessagingException {

        /*User user = User.builder()
                .email(request.getEmail())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .createAt(LocalDateTime.now())
                .role(Role.CUSTOMER)
                .build();

        userRepository.save(user);*/

        // GUARDAR TAMBIEN EN CUSTOMER

        /*Customer customer = new Customer();

        // Copia los datos de user a customer
        BeanUtils.copyProperties(user, customer);

        customer.setPhone(request.getPhone());
        customer.setBirthdate(request.getBirthdate());

        customerRepository.save(customer)*/;

        Customer user = Customer.builder()
                .email(request.getEmail())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .createAt(LocalDateTime.now())
                .phone(request.getPhone())
                .birthdate(request.getBirthdate())
                .role(Role.CUSTOMER)
                .build();

        customerRepository.save(user);

        String token = jwtService.getToken(user, user.getId());

        /*EmailDTO email = EmailDTO.builder()
                .addressee(user.getEmail())
                .subjet("¡Tu cuenta ha sido creada! | SPA SENTIRSE BIEN")
                .message(user.getFirstName() + ", " + user.getLastName()) // de momento lo usuamos para pasar el nombre del usuario
                .build();

        emailService.sendEmail(email);*/

        // Envío asíncrono del correo para que no bloquee la respuesta al usuario
        taskExecutor.execute(() -> {
            try {
                EmailRegisterDTO email = EmailRegisterDTO.builder()
                        .addressee(user.getEmail())
                        .subjet("¡Tu cuenta ha sido creada! | SPA SENTIRSE BIEN")
                        .message(user.getFirstName() + ", " + user.getLastName())
                        .build();
                emailService.sendEmail(email);
            } catch (Exception e) {
                // Loggear el error pero no interrumpir el flujo
                System.err.println("Error enviando email: " + e.getMessage());
            }
        });

        return AuthResponse.builder()
                .status("Success")
                .message("User successfully registered")
                .idUser(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .rol(user.getRole().name())
                .token(token)
                .build();

    }

}
