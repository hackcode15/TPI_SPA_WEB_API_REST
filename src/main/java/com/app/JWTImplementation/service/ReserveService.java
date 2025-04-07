package com.app.JWTImplementation.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    
}
