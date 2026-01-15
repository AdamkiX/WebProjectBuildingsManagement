package com.tab.buildings.controller;

import com.tab.buildings.entity.Apartment;
import com.tab.buildings.entity.Rent;
import com.tab.buildings.rep.ApartmentRepository;
import com.tab.buildings.rep.RentRepository;

import com.tab.buildings.security.AuthService;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Date;
import java.time.LocalDateTime;
import java.time.Period;

import com.tab.buildings.wrapper.DatePayments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class DatePaymentsController {

    @Autowired
    private AuthService authService;
    @Autowired
    private ApartmentRepository apartmentRepository;
    @Autowired
    private RentRepository rentRepository;


    /** Function return List of DatePayments class
     * to check how much money per put date apartment earn
     * input are two dates, from and to as from date to date
     */
    @PostMapping("/manager/paymentsInfo")
    public ResponseEntity<Object> getSummaryPayments(Date from,Date to)
    {
        Integer id = authService.getLoggedUser();
        if(Objects.isNull(id)){
            return ResponseEntity.status(401).body("Log is first as manager");
        }
        if(from.after(to))
        {
            return ResponseEntity.status(401).body("Wrong time selected");
        }

        List<DatePayments> paymentsList = new ArrayList<>();
        List<Rent> rents = new ArrayList<>();
        List<Apartment> apartments =  new ArrayList<>();
        Iterable<Rent> allRents = rentRepository.findAll();
        Iterable<Apartment> allApartments = apartmentRepository.findAll();
        {
            for (Apartment apartment : allApartments) {
                if (apartment.getIdManager().equals(id)) {
                    apartments.add(apartment);
                }
            }
            for (Rent rent : allRents) {
                for (Apartment apartment : apartments) {
                    if (rent.getIdApartment().equals(apartment.getId())) {
                        rents.add(rent);
                    }
                }
            }
        }

        LocalDateTime dateTimeFrom = LocalDateTime.of(from.getYear(),from.getMonth()+1,from.getDate(),0,0);
        LocalDateTime dateTimeTo = LocalDateTime.of(to.getYear(),to.getMonth()+1,to.getDate(),0,0);
        Period period = Period.between(dateTimeFrom.toLocalDate(),dateTimeTo.toLocalDate());

        int years = period.getYears();
        int months = period.getMonths();
        Integer totalMonths = years * 12 + months;
        for(Rent rent: rents)
        {
            for(Apartment apartment : apartments)
            {
                Integer apt_number = apartment.getApt_number();
                Double sum = rent.getAmount() * totalMonths;
                DatePayments dp = new DatePayments(apt_number,sum);
                paymentsList.add(dp);
            }
        }
        return ResponseEntity.status(200).body(ResponseEntity.ok(paymentsList));
    }

    /** Function return List of DatePayments class
     * to check how much money per put date apartment earn
     * there is no input so there will be information about payments for 12 months
     */
    @GetMapping("/manager/paymentsInfo")
    public ResponseEntity<Object> getSummaryPayments()
    {
        Integer id = authService.getLoggedUser();
        if(Objects.isNull(id)){
            return ResponseEntity.status(401).body("Log is first as manager");
        }

        List<DatePayments> paymentsList = new ArrayList<>();
        List<Rent> rents = new ArrayList<>();
        List<Apartment> apartments =  new ArrayList<>();
        Iterable<Rent> allRents = rentRepository.findAll();
        Iterable<Apartment> allApartments = apartmentRepository.findAll();
        {
            for (Apartment apartment : allApartments) {
                if (apartment.getIdManager().equals(id)) {
                    apartments.add(apartment);
                }
            }
            for (Rent rent : allRents) {
                for (Apartment apartment : apartments) {
                    if (rent.getIdApartment().equals(apartment.getId())) {
                        rents.add(rent);
                    }
                }
            }
        }

        for(Rent rent: rents)
        {
            for(Apartment apartment : apartments)
            {
                Integer apt_number = apartment.getApt_number();
                Double sum = rent.getAmount() * 12;
                DatePayments dp = new DatePayments(apt_number,sum);
                paymentsList.add(dp);
            }
        }

        return ResponseEntity.status(200).body(ResponseEntity.ok(paymentsList));
    }
}
