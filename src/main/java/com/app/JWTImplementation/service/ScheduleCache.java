package com.app.JWTImplementation.service;

import com.app.JWTImplementation.model.Schedule;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ScheduleCache {

    private final Map<LocalDate, Map<Integer, List<Schedule>>> cache = new ConcurrentHashMap<>();

    public void store(LocalDate date, Integer serviceId, List<Schedule> schedules) {
        cache.computeIfAbsent(date, k -> new ConcurrentHashMap<>())
                .put(serviceId, schedules);
    }

    public Optional<Schedule> find(Integer serviceId, LocalDateTime start) {
        return Optional.ofNullable(cache.get(start.toLocalDate()))
                .map(m -> m.get(serviceId))
                .flatMap(list -> list.stream()
                        .filter(s -> s.getStartDatetime().equals(start))
                        .findFirst());
    }

}
