package com.tab.buildings.service;

import com.tab.buildings.entity.Rent;
import com.tab.buildings.entity.RentAgreement;
import com.tab.buildings.security.ReqRes;
import org.springframework.http.ResponseEntity;

public interface RentService {


    public ReqRes fetchRentById(Integer id_rent);

    public ReqRes fetchAllRents();

    public ReqRes addNewRent(Rent rent);

    public ReqRes updateIfExists(Rent rent);

    public ReqRes deleteRentById(Integer id_rent);
}
