package com.app.JWTImplementation.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class DemoController {
    
    @PostMapping("/demo")
    public String welcome() {
        return "Welcome Diego!";
    }


}
