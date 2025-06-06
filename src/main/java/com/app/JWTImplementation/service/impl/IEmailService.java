package com.app.JWTImplementation.service.impl;

import com.app.JWTImplementation.dto.EmailDTO;
import jakarta.mail.MessagingException;

public interface IEmailService {
    public void sendEmail(EmailDTO email) throws MessagingException;
}
