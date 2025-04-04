package com.app.JWTImplementation.controller;


import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.JWTImplementation.dto.responses.ApiResponse;
import com.app.JWTImplementation.model.Schedule;
import com.app.JWTImplementation.service.ScheduleService;

@RestController
@RequestMapping("/api/schedule")
public class ScheduleController {
    
    @Autowired
    private ScheduleService service;

    @GetMapping
    @ResponseBody
    public ResponseEntity<ApiResponse<List<Schedule>>> getAllSchedules() {
        
        List<Schedule> schedules = service.findAllSchedules();

        ApiResponse<List<Schedule>> response = new ApiResponse<>(
            "Success",
            "Schedule retrived succesfully",
            schedules
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
        
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<ApiResponse<Schedule>> getSchedule (@PathVariable("id") Integer id) {
        
        Schedule schedule = service.findScheduleById(id);

        ApiResponse<Schedule> response = new ApiResponse<>(
            "Success",
            "User successfully recovered",
            schedule
        );

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @GetMapping("/date/{date}")
    @ResponseBody
    public ResponseEntity<ApiResponse<List<Schedule>>> getSchedulesByDate(@PathVariable("date") LocalDate date) {
    
        List<Schedule> schedules = service.findSchedulesByDate(date);

        ApiResponse<List<Schedule>> response = new ApiResponse<>(
            "Success",
            "Schedule retrived succesfully",
            schedules
        );

        return new ResponseEntity<>(response, HttpStatus.OK);

    }
    


}
