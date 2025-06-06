package com.app.JWTImplementation.service.impl;

import com.app.JWTImplementation.dto.admin.SaveNewUserDTO;

public interface IAdminService {
    public String creationNewUser(SaveNewUserDTO userDetails);
}
