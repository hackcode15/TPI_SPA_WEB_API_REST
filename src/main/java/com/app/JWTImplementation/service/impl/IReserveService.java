package com.app.JWTImplementation.service.impl;

import java.util.List;

import com.app.JWTImplementation.dto.ReserveRequestDTO;
import com.app.JWTImplementation.model.Reserve;

public interface IReserveService {
    
    public List<Reserve> findAllReserves();
    public Reserve saveReserve(ReserveRequestDTO reserveDetails);
    public Reserve findReserveById(Integer id);
    public void deleteReserveById(Integer id);

}
