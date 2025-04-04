package com.app.JWTImplementation.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.JWTImplementation.dto.ReserveRequestDTO;
import com.app.JWTImplementation.exceptions.ReserveNotFoundException;
import com.app.JWTImplementation.exceptions.ScheduleNotFoundException;
import com.app.JWTImplementation.exceptions.ServiceSpaNotFoundException;
import com.app.JWTImplementation.exceptions.UserNotFoundException;
import com.app.JWTImplementation.model.Reserve;
import com.app.JWTImplementation.model.Schedule;
import com.app.JWTImplementation.model.ServiceSpa;
import com.app.JWTImplementation.model.User;
import com.app.JWTImplementation.repository.ReserveRepository;
import com.app.JWTImplementation.repository.ScheduleRepository;
import com.app.JWTImplementation.repository.ServiceSpaRepository;
import com.app.JWTImplementation.repository.UserRepository;
import com.app.JWTImplementation.service.impl.IReserveService;

import jakarta.transaction.Transactional;

@Service
public class ReserveService implements IReserveService {

    @Autowired
    private ReserveRepository repository;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ServiceSpaRepository serviceSpaRepository;
    
    @Autowired
    private ScheduleRepository scheduleRepository;

    @Override
    public List<Reserve> findAllReserves() {
        return repository.findAll();    
    }

    @Transactional
    @Override
    public Reserve saveReserve(ReserveRequestDTO reserveDetails) {

        User user = userRepository.findById(reserveDetails.getUserID())
            .orElseThrow(() -> new UserNotFoundException(reserveDetails.getUserID()));
        
        ServiceSpa service = serviceSpaRepository.findById(reserveDetails.getServiceSpaID())
            .orElseThrow(() -> new ServiceSpaNotFoundException(reserveDetails.getServiceSpaID()));
        
        Schedule schedule = scheduleRepository.findById(reserveDetails.getScheduleID())
            .orElseThrow(() -> new ScheduleNotFoundException(reserveDetails.getScheduleID()));

        if(!schedule.isAvailable()) {
            throw new RuntimeException("El horario seleccionado no está disponible");
        }
        
        Reserve newReserveCreated = Reserve.builder()
            .user(user)
            .service(service)
            .schedule(schedule)
            .reservationDate(LocalDate.now())
            .build();

        schedule.setAvailable(false);
        scheduleRepository.save(schedule);

        return repository.save(newReserveCreated);
    
    }

    @Override
    public Reserve findReserveById(Integer id) {
        return repository.findById(id)
            .orElseThrow(() -> new ReserveNotFoundException(id));    
    }

    @Override
    public void deleteReserveById(Integer id) {
        Reserve reserve = this.findReserveById(id);
        repository.delete(reserve);  
    }
    
}
