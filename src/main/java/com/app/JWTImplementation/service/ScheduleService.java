package com.app.JWTImplementation.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.app.JWTImplementation.dto.ScheduleDTO;
import com.app.JWTImplementation.dto.ScheduleInfoDTO;
import com.app.JWTImplementation.dto.projection.ScheduleProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.JWTImplementation.exceptions.ScheduleNotFoundException;
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
            .orElseThrow(() -> new ScheduleNotFoundException(id));    
    }

    @Override
    public void deleteById(Integer id) {
        Schedule schedule = this.findScheduleById(id);
        repository.delete(schedule);    
    }

    // Metodos sacados directo del Repository
    public List<ScheduleInfoDTO> findSchedulesByDate(LocalDate date) {

        List<ScheduleProjection> scheduleProjections = repository.findByDate(date);

        List<ScheduleInfoDTO> scheduleDTO = scheduleProjections.stream()
                .map(schedule -> {

                    ScheduleInfoDTO dto = ScheduleInfoDTO.builder()
                            .id(schedule.getId())
                            .startDate(schedule.getStartDatetime().toLocalDate())
                            .startTime(schedule.getStartDatetime().toLocalTime())
                            .endDate(schedule.getEndDatetime().toLocalDate())
                            .endTime(schedule.getEndDatetime().toLocalTime())
                            .maxCapacity(schedule.getMaxCapacity())
                            .currentCapacity(schedule.getCurrentCapacity())
                            .isActive(schedule.getIsActive())
                            .service(schedule.getServiceName())
                            .build();

                    return dto;

                }).collect(Collectors.toList());

        return scheduleDTO;

    }

    public List<ScheduleInfoDTO> findSchedulesOfServiceByDate(LocalDate date, Integer serviceId) {

        List<ScheduleProjection> scheduleProjections = repository.findAllSchedulesOfServiceByDate(date, serviceId);

        List<ScheduleInfoDTO> scheduleDTO = scheduleProjections.stream()
                .map(schedule -> {

                    ScheduleInfoDTO dto = ScheduleInfoDTO.builder()
                            .id(schedule.getId())
                            .startDate(schedule.getStartDatetime().toLocalDate())
                            .startTime(schedule.getStartDatetime().toLocalTime())
                            .endDate(schedule.getEndDatetime().toLocalDate())
                            .endTime(schedule.getEndDatetime().toLocalTime())
                            .maxCapacity(schedule.getMaxCapacity())
                            .currentCapacity(schedule.getCurrentCapacity())
                            .isActive(schedule.getIsActive())
                            .service(schedule.getServiceName())
                            .build();

                    return dto;

                }).collect(Collectors.toList());

        return scheduleDTO;

    }

    public List<ScheduleInfoDTO> findAllSchedulesWithEntities(){

        List<ScheduleProjection> scheduleProjections = repository.findAllSchedulesWithEntities();

        List<ScheduleInfoDTO> scheduleDTO = scheduleProjections.stream()
                .map(schedule -> {

                    ScheduleInfoDTO dto = ScheduleInfoDTO.builder()
                            .id(schedule.getId())
                            .startDate(schedule.getStartDatetime().toLocalDate())
                            .startTime(schedule.getStartDatetime().toLocalTime())
                            .endDate(schedule.getEndDatetime().toLocalDate())
                            .endTime(schedule.getEndDatetime().toLocalTime())
                            .maxCapacity(schedule.getMaxCapacity())
                            .currentCapacity(schedule.getCurrentCapacity())
                            .isActive(schedule.getIsActive())
                            .service(schedule.getServiceName())
                            .build();

                    return dto;

                }).collect(Collectors.toList());

        return scheduleDTO;

    }

    public ScheduleInfoDTO findScheduleByIdWithEntity(Integer id){

        ScheduleProjection schedule = repository.findScheduleByIdWithEntity(id)
                .orElseThrow(() -> new ScheduleNotFoundException(id));

        ScheduleInfoDTO scheduleDTO = ScheduleInfoDTO.builder()
                .id(schedule.getId())
                .startDate(schedule.getStartDatetime().toLocalDate())
                .startTime(schedule.getStartDatetime().toLocalTime())
                .endDate(schedule.getEndDatetime().toLocalDate())
                .endTime(schedule.getEndDatetime().toLocalTime())
                .maxCapacity(schedule.getMaxCapacity())
                .currentCapacity(schedule.getCurrentCapacity())
                .isActive(schedule.getIsActive())
                .service(schedule.getServiceName())
                .build();

        return scheduleDTO;

    }

}
