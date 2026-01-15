package com.tab.buildings.service;

import com.tab.buildings.entity.Apartment;
import com.tab.buildings.security.ReqRes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

public interface ApartmentService {
    // save operation
    ReqRes saveApartment(Apartment apartment);

    // update operation
    ReqRes updateApartment(Apartment apartment, Integer apartmentId);

    // delete operation
    ReqRes deleteApartmentById(Integer apartmentId);

    // delete tenant from apartment
    ReqRes deleteTenantFromApartment(Integer apartmentId);

    ReqRes findApartmentsByBuildingId(Integer buildingId);

    public ReqRes fetchApartment(@PathVariable("id") Integer id_apartment);

    ReqRes addTenant(Integer idApartment, Integer idTenant);

    public ReqRes findApartmentsWithRoomsByBuildingId(Integer idBuilding);

    ReqRes findAllTenantApartments();
}
