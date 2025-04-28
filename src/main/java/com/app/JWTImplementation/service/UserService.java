package com.app.JWTImplementation.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.app.JWTImplementation.dto.responses.UserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.JWTImplementation.dto.UserDTO;
import com.app.JWTImplementation.exceptions.UserNotFoundException;
import com.app.JWTImplementation.model.User;
import com.app.JWTImplementation.model.User.Role;
import com.app.JWTImplementation.repository.UserRepository;
import com.app.JWTImplementation.service.impl.IUserService;

@Slf4j
@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserResponse findUserByUsername(String username) {

        /*User user = repository.findUserByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));*/

        Optional<User> userOptional = repository.findUserByUsername(username);

        if (userOptional.isEmpty()) {
            log.debug("Usuario no encontrado con username: {}", username);
            throw new UserNotFoundException("User not found whith username: " + username);
        }

        User user = userOptional.get();

        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getFirstName(),
                user.getLastName(),
                user.getCreateAt(),
                user.getUpdateAt(),
                user.getRole()
        );

    }

    /*@Override
    public List<User> findAllUsers() {

        return null;

    }*/

    @Override
    public List<UserResponse> findAllUsers() {

        /*List<User> users = repository.findAll();

        return users.stream()
                .map(UserResponse::fromUser)
                .toList();*/

        return repository.findAll().stream()
                .map(UserResponse::fromUser)
                .toList();

    }

    /*@Override
    public User saveUser(UserDTO userDetails) {

        User user = User.builder()
            .username(userDetails.getUsername())
            .password(passwordEncoder.encode(userDetails.getPassword()))
            .firstName(userDetails.getFirstName())
            .lastName(userDetails.getLastName())
            .createAt(LocalDateTime.now())
            .role(Role.CUSTOMER)
            .build();

        return repository.save(user);

    }*/

    @Override
    public UserResponse saveUser(UserDTO userDetails) {

        User user = userDetails.toEntity(passwordEncoder);
        User savedUser = repository.save(user);
        return UserResponse.fromUser(savedUser);

    }

    /*@Override
    public User findUserById(Integer id) {
        return repository.findById(id)
            .orElseThrow(() -> new UserNotFoundException("Error User not found, ID: " + id + " no exist"));    
    }*/

    @Override
    public UserResponse findUserById(Integer id) {

        return repository.findById(id)
                .map(UserResponse::fromUser)
                .orElseThrow(() -> new UserNotFoundException(id));

    }

    @Override
    public void deleteUserById(Integer id) {
        User user = repository.findById(id)
                        .orElseThrow(() -> new UserNotFoundException(id));
        repository.deleteById(user.getId());
    }

    /*@Override
    public User updateUserById(Integer id, UserDTO userDetails) {

        //User user = this.findUserById(id);
        User user = repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        user.setUsername(userDetails.getUsername());

        if(userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDetails.getPassword())); // encriptar la contraseña nueva
        }

        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        user.setUpdateAt(LocalDateTime.now());
        user.setRole(Role.CUSTOMER);

        return repository.save(user);
    
    }*/

    @Override
    public UserResponse updateUserById(Integer id, UserDTO userDetails) {

        //User user = this.findUserById(id);
        User user = repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        user.setUsername(userDetails.getUsername());

        if(userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDetails.getPassword())); // encriptar la contraseña nueva
        }

        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        user.setUpdateAt(LocalDateTime.now());
        user.setRole(Role.CUSTOMER);

        User updateUser = repository.save(user);

        return UserResponse.fromUser(updateUser);

    }
    
}
