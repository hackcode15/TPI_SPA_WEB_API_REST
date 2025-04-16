package com.app.JWTImplementation.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.JWTImplementation.dto.UserDTO;
import com.app.JWTImplementation.exceptions.UserNotFoundException;
import com.app.JWTImplementation.model.User;
import com.app.JWTImplementation.model.User.Role;
import com.app.JWTImplementation.repository.UserRepository;
import com.app.JWTImplementation.service.impl.IUserService;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<User> findAllUsers() {
        return repository.findAll();    
    }

    @Override
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

    }

    @Override
    public User findUserById(Integer id) {
        return repository.findById(id)
            .orElseThrow(() -> new UserNotFoundException("Error User not found, ID: " + id + " no exist"));    
    }

    @Override
    public void deleteUserById(Integer id) {
        User user = this.findUserById(id);
        repository.deleteById(user.getId());    
    }

    @Override
    public User updateUserById(Integer id, UserDTO userDetails) {
        
        User user = this.findUserById(id);

        user.setUsername(userDetails.getUsername());

        if(userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDetails.getPassword())); // encriptar la contraseña nueva
        }

        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        user.setUpdateAt(LocalDateTime.now());
        user.setRole(Role.CUSTOMER);

        return repository.save(user);
    
    }
    
}
