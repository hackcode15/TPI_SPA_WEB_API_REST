package com.app.JWTImplementation.service.impl;

import com.app.JWTImplementation.dto.EmailRegisterDTO;
import com.app.JWTImplementation.dto.EmailInvoiceDTO;
import jakarta.mail.MessagingException;

public interface IEmailService {
    public void sendEmail(EmailRegisterDTO email) throws MessagingException;
    public void sendInvoiceEmail(EmailInvoiceDTO emailDTO) throws MessagingException;
}
