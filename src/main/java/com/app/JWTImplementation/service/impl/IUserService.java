package com.app.JWTImplementation.service.impl;

import java.util.List;

import com.app.JWTImplementation.dto.UserDTO;
import com.app.JWTImplementation.model.User;

public interface IUserService {
    
    public List<User> findAllUsers();
    public User saveUser(UserDTO userDetails);
    public User findUserById(Integer id);

    public User updateUserById(Integer id, UserDTO userDetails);
    

    //public User updateUserPartial(JsonPatch patch, User targetUser) throws JsonPatchException, JsonProcessingException;

    public void deleteUserById(Integer id);

}
