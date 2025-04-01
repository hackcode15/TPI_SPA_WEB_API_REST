package com.app.JWTImplementation.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.JWTImplementation.dto.UserUpdateDTO;
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
    public User saveUser(User user) {
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
    public User updateUserById(Integer id, UserUpdateDTO userDetails) {
        
        User user = this.findUserById(id);

        user.setUsername(userDetails.getUsername());

        if(userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDetails.getPassword())); // encriptar la contraseña nueva
        }

        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        user.setCountry(userDetails.getCountry());
        user.setRole(Role.USER);

        return this.saveUser(user);
    
    }
    
}
