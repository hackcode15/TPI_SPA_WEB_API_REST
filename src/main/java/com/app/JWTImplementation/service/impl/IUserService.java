package com.app.JWTImplementation.service.impl;

import java.util.List;

import com.app.JWTImplementation.dto.UserDTO;
import com.app.JWTImplementation.dto.responses.UserResponse;
import com.app.JWTImplementation.model.User;

public interface IUserService {
    
    //public List<User> findAllUsers();
    public List<UserResponse> findAllUsers(); // Optimizado

    //public User saveUser(UserDTO userDetails);
    public UserResponse saveUser(UserDTO userDetails); // Optimizado

    //public User findUserById(Integer id);
    public UserResponse findUserById(Integer id); // Optimizado

    //public User updateUserById(Integer id, UserDTO userDetails);
    public UserResponse updateUserById(Integer id, UserDTO userDetails);

    //public User updateUserPartial(JsonPatch patch, User targetUser) throws JsonPatchException, JsonProcessingException;

    public void deleteUserById(Integer id);

}
