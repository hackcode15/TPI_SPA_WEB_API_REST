package com.app.JWTImplementation.controller;

import com.app.JWTImplementation.dto.EmailDTO;
import com.app.JWTImplementation.service.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/send-email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping
    private ResponseEntity<String> sendEmail(@RequestBody EmailDTO email) throws MessagingException {
        emailService.sendEmail(email);
        return new ResponseEntity<>("Email sending successfully", HttpStatus.OK);
    }

}