package com.app.JWTImplementation.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.app.JWTImplementation.dto.ReserveDTO;
import com.app.JWTImplementation.exceptions.ScheduleNotFoundException;
import com.app.JWTImplementation.exceptions.UserNotFoundException;
import com.app.JWTImplementation.model.Schedule;
import com.app.JWTImplementation.model.User;
import com.app.JWTImplementation.repository.ScheduleRepository;
import com.app.JWTImplementation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.JWTImplementation.dto.ReserveInfoDTO;
import com.app.JWTImplementation.dto.projection.ReserveProjection;
import com.app.JWTImplementation.exceptions.ReserveNotFoundException;
import com.app.JWTImplementation.model.Reserve;
import com.app.JWTImplementation.repository.ReserveRepository;
import com.app.JWTImplementation.service.impl.IReserveService;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReserveService implements IReserveService {

    @Autowired
    private ReserveRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Override
    public List<Reserve> findAllReserves() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public Reserve saveReserve(Reserve reserve) {
        return repository.save(reserve);
    }

    @Override
    @Transactional
    public Reserve updateReserve(Integer id, ReserveDTO reserveDetails) {

        Reserve reserve = this.findReserveById(id);
        User user = userRepository.findById(reserveDetails.getUserId())
                .orElseThrow(() -> new UserNotFoundException(id));
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new ScheduleNotFoundException(id));

        reserve.setDateReserve(LocalDateTime.now());
        reserve.setUser(user);
        reserve.setSchedule(schedule);
        reserve.setStatus(reserveDetails.getStatus());

        return reserve;

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

    // metodos sacado desde el repositorio
    public List<ReserveInfoDTO> findAllReservesWhitEntities() {

        List<ReserveProjection> reserveProjections = repository.findAllReservesProjection();

        List<ReserveInfoDTO> reservesDTO = reserveProjections.stream()
                .map(reserve -> {

                    ReserveInfoDTO dto = ReserveInfoDTO.builder()
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

    public ReserveInfoDTO findReserveWithEntityById(Integer id) {

        ReserveProjection reserve = repository.findReserveProjectionById(id)
                .orElseThrow(() -> new ReserveNotFoundException(id));

        ReserveInfoDTO reserveDTO = ReserveInfoDTO.builder()
                .id(reserve.getId())
                .dateReserve(reserve.getDateReserve().toLocalDate())
                .userFullName(reserve.getUserFullName())
                .serviceName(reserve.getServiceName())
                .startDate(reserve.getScheduleStart().toLocalDate())
                .startTime(reserve.getScheduleStart().toLocalTime())
                .endTime(reserve.getScheduleEnd().toLocalTime())
                .status(reserve.getStatus())
                .build();

        return reserveDTO;

    }

}
