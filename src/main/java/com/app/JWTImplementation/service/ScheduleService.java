package com.app.JWTImplementation.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.JWTImplementation.model.Schedule;
import com.app.JWTImplementation.repository.ScheduleRepository;
import com.app.JWTImplementation.service.impl.IScheduleService;

@Service
public class ScheduleService implements IScheduleService {

    @Autowired
    private ScheduleRepository repository;

    @Override
    public List<Schedule> findAllSchedules() {
        return repository.findAll();    
    }

    @Override
    public Schedule saveSchedule(Schedule schedule) {
        return repository.save(schedule);    
    }

    @Override
    public Schedule findScheduleById(Integer id) {
        return repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Error horario no encontrado"));    
    }

    @Override
    public void deleteById(Integer id) {
        Schedule schedule = this.findScheduleById(id);
        repository.delete(schedule);    
    }
    
}
