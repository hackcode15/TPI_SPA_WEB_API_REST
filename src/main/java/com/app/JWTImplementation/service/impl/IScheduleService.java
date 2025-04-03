package com.app.JWTImplementation.service.impl;

import java.util.List;

import com.app.JWTImplementation.model.Schedule;

public interface IScheduleService {
    
    public List<Schedule> findAllSchedules();
    public Schedule saveSchedule(Schedule schedule);
    public Schedule findScheduleById(Integer id);
    public void deleteById(Integer id);

}
