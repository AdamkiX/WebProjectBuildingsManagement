package com.tab.buildings.service;

import com.tab.buildings.controller.ApartmentController;
import com.tab.buildings.entity.*;
import com.tab.buildings.rep.*;
import com.tab.buildings.security.AuthService;
import com.tab.buildings.security.ReqRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class RentServiceImpl implements RentService {


    @Autowired
    private ApartmentRepository apartmentRepository;
    @Autowired
    private RentRepository rentRepository;
    @Autowired
    private AuthService authService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ApartmentController apartmentController;


    public ReqRes fetchRentById(Integer id_rent){
        //variables
        ReqRes reqRes = new ReqRes();
        Iterable<Apartment> apartments = apartmentRepository.findAll();
        Iterable<Rent> rents = rentRepository.findAll();
        Rent rent;
        Apartment apartment;

        ///security
        Integer id = authService.getLoggedUser();
        // finding exact rent
        try{
            rent = rentRepository.findById(id_rent).orElseThrow(() -> new RuntimeException("Apartment not found"));
        }catch (Exception e){
            reqRes.setStatusCode(404);  // Error: no rent found
            return reqRes;
        }
        try {
            apartment = apartmentRepository.findById(rent.getIdApartment())
                    .orElseThrow(() -> new RuntimeException("Apartment not found"));
        } catch (Exception e) {
            reqRes.setStatusCode(500);  // Error: exists in  rents and not in apartments
            return reqRes;
        }
        if(rent.getIdApartment() == apartment.getId() && (Objects.equals(id, apartment.getIdManager()) || Objects.equals(id, apartment.getIdTenant()))) {
            reqRes.setRent(rent);
            reqRes.setStatusCode(200);  // ok
            return reqRes;
        }else {
            reqRes.setRent(null);
            reqRes.setStatusCode(403);  // forbidden
            return reqRes;
        }
    };

    public ReqRes fetchAllRents(){                  ///////works, fetches all associated with you rents
        //variables
        ReqRes reqRes = new ReqRes();
        Iterable<Apartment> apartments = apartmentRepository.findAll();
        Iterable<Rent> rents = rentRepository.findAll();
        Apartment apartment;
        List<Rent> newRents = new ArrayList<>();
        ///security
        Integer id = authService.getLoggedUser();
        // finding exact rent
        for(Rent rent : rents){
            try{
            apartment = apartmentRepository.findById(rent.getIdApartment()).orElseThrow();
            }catch (Exception e){
                reqRes.setStatusCode(500);      // apartment doesn't exist and rent pointing to it does
                return reqRes;
            }
            if (Objects.equals(apartment.getIdManager(), id) || Objects.equals(apartment.getIdTenant(), id)){
                newRents.add(rent);
            }
        }
        reqRes.setRents(newRents);
        if(reqRes.getRents().isEmpty()){
            reqRes.setStatusCode(404);  //nothing belonging to user found
            return reqRes;
        }
        reqRes.setStatusCode(200);  // ok
        return reqRes;
    };


    public ReqRes updateIfExists(Rent rent) {
        ReqRes reqRes = new ReqRes();

        try {
            Rent rentDB = rentRepository.findById(rent.getId()).orElseThrow(() -> new RuntimeException("Rent record not found"));

            if (Objects.nonNull(rent.getIdApartment())) {
                rentDB.setIdApartment(rent.getIdApartment());
                Apartment apartment = apartmentRepository.findById(rent.getIdApartment()).orElseThrow();
                if (apartment.getIdManager() != apartmentRepository.findById(rent.getIdApartment()).get().getIdManager()) {
                    throw new RuntimeException("Apartment not yours");
                }
            } else {
                throw new RuntimeException("Apartment ID is null");
            }

            if (Objects.nonNull(rent.getAddDate())) {
                rentDB.setAddDate(rent.getAddDate());
            }

            if (Objects.nonNull(rent.getStartDate())) {
                rentDB.setStartDate(rent.getStartDate());
            }

            if (Objects.nonNull(rent.getEndDate())) {
                rentDB.setEndDate(rent.getEndDate());
            }

            if (Objects.nonNull(rent.getInformation())) {
                rentDB.setInformation(rent.getInformation());
            }

            if (Objects.nonNull(rent.getAmount())) {
                rentDB.setAmount(rent.getAmount());
            }

            if (Objects.nonNull(rent.isPayed())) {
                rentDB.setPayed(rent.isPayed());
            }

            rentRepository.save(rentDB);
            reqRes.setStatusCode(200);  // ok

        } catch (Exception e) {
            reqRes.setStatusCode(400);
        }

        return reqRes;
    }


    public ReqRes addNewRent(Rent rent){
        //variables

        ReqRes reqRes = new ReqRes();

        rent.setId(Integer.MAX_VALUE);
        Rent rentDB = new Rent();
        try {
            if (Objects.nonNull(rent.getIdApartment())) {
                rentDB.setIdApartment(rent.getIdApartment());
                try{
                    Apartment apartment = apartmentRepository.findById(rent.getIdApartment()).orElseThrow();
                    if(apartment.getIdManager() != rent.getIdApartment()){
                        throw new RuntimeException("Apartment not yours");
                    }

                }catch (Exception e){
                    reqRes.setStatusCode(400);
                    return reqRes;
                }
            }else{
                throw new RuntimeException();
            }
            if (Objects.nonNull(rent.getAddDate())) {
                rentDB.setAddDate(rent.getAddDate());
            }else{
                LocalDate currentDate = LocalDate.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                rentDB.setAddDate(Date.valueOf(currentDate));
            }
            if (Objects.nonNull(rent.getStartDate())) {
                rentDB.setStartDate(rent.getStartDate());
            }else{
                LocalDate currentDate = LocalDate.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                rentDB.setStartDate(Date.valueOf(currentDate));
            }
            if (Objects.nonNull(rent.getEndDate())) {
                rentDB.setEndDate(rent.getEndDate());
            }else{
                LocalDate currentDate = LocalDate.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                rentDB.setEndDate(Date.valueOf(currentDate));
            }
            if (Objects.nonNull(rent.getInformation())) {
                rentDB.setInformation(rent.getInformation());
            }else{
                rentDB.setInformation(null);
            }
            if (Objects.nonNull(rent.getAmount())) {
                rentDB.setAmount(rent.getAmount());
            }else{
                rentDB.setAmount(null);
            }
            if (Objects.nonNull(rent.isPayed())) {
                rentDB.setPayed(rent.isPayed());
            }
        } catch (Exception e) {
            reqRes.setStatusCode(400);
            return reqRes;
        }
        rentRepository.save(rent);
        reqRes.setStatusCode(200);  // ok
        return reqRes;
    };

    public ReqRes deleteRentById(Integer id_rent){

        ReqRes reqRes = new ReqRes();
        Rent rent = new Rent();
        try{
           rent = rentRepository.findById(id_rent).orElseThrow();
            try {
                Apartment apartment = apartmentRepository.findById(rent.getIdApartment()).orElseThrow();
                try{
                    if(apartment.getIdManager() != rent.getIdApartment()){
                        throw new RuntimeException();
                    }else{
                        rentRepository.delete(rent);
                        reqRes.setStatusCode(200);
                        return reqRes;
                    }
                }catch (Exception e){
                    reqRes.setStatusCode(403);
                    reqRes.setMessage("Rent not yours to decide");
                    return reqRes;
                }
            }catch(Exception e){
                reqRes.setStatusCode(400);
                reqRes.setMessage("Apartment not found");
                return reqRes;
            }
        }catch (Exception e){
            reqRes.setStatusCode(400);
            reqRes.setMessage("Rent not found");
            return reqRes;
        }

    };
}
