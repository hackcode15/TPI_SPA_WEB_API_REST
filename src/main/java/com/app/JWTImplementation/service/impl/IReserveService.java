package com.app.JWTImplementation.service.impl;

import java.util.List;

import com.app.JWTImplementation.dto.ReserveDTO;
import com.app.JWTImplementation.model.Reserve;

public interface IReserveService {
 
    public List<Reserve> findAllReserves();
    public Reserve saveReserve(ReserveDTO reserve);
    public Reserve updateReserve(Integer id, ReserveDTO reserveDetails);
    public Reserve findReserveById(Integer id);
    public void deleteReserveById(Integer id);
    
}
