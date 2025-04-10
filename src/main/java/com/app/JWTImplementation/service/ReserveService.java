package com.app.JWTImplementation.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.JWTImplementation.dto.ReserveDTO;
import com.app.JWTImplementation.dto.projection.ReserveProjection;
import com.app.JWTImplementation.exceptions.ReserveNotFoundException;
import com.app.JWTImplementation.model.Reserve;
import com.app.JWTImplementation.repository.ReserveRepository;
import com.app.JWTImplementation.service.impl.IReserveService;

@Service
public class ReserveService implements IReserveService {

    @Autowired
    private ReserveRepository repository;

    @Override
    public List<Reserve> findAllReserves() {
        return repository.findAll();    
    }

    @Override
    public Reserve saveReserve(Reserve reserve) {
        return repository.save(reserve);    
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

    // metodo sacado desde el repositorio
    public List<ReserveDTO> findAllReservesWhitEntities() {
        
        List<ReserveProjection> reserveProjections = repository.findAllReservesProjection();

        List<ReserveDTO> reservesDTO = reserveProjections.stream()
            .map(reserve -> {
                
                ReserveDTO dto = ReserveDTO.builder()
                    .id(reserve.getId())
                    .dateReserve(reserve.getDateReserve().toLocalDate())
                    .userFullName(reserve.getUserFullName())
                    .serviceName(reserve.getServiceName())
                    .startDate(reserve.getScheduleStart().toLocalDate())
                    .startTime(reserve.getScheduleStart().toLocalTime())
                    .endTime(reserve.getScheduleEnd().toLocalTime())
                    .status(reserve.getStatus())
                    .build();

                return dto;

            }).collect(Collectors.toList());

        return reservesDTO;

    }

}
