package com.tab.buildings.service;

import com.tab.buildings.entity.*;
import com.tab.buildings.rep.*;
import com.tab.buildings.security.AuthService;
import com.tab.buildings.security.ReqRes;
import com.tab.buildings.wrapper.RentsPayments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PaymentsServiceImpl implements PaymentsService {

    @Autowired
    private PaymentsRepository paymentsRepository;
    @Autowired
    private AuthService authService;
    @Autowired
    private ApartmentRepository apartmentRepository;
    @Autowired
    private RentAgreementRepository rentAgreementRepository;
    @Autowired
    private RentRepository rentRepository;


    public ReqRes pay(Payment payment){
        ReqRes reqRes = new ReqRes();
        LocalDate localDate = LocalDate.now(); // Get current date
        Optional<User> user = authService.getUser();

        try{
            payment.setId_payment(-1);
            payment.setPayment_date(Date.valueOf(localDate));
            if(payment.getAmount() <= 0){
                throw new Exception();
            }
            if(!Objects.equals(apartmentRepository.findById(rentRepository.findById(payment.getIdRent()).get().getIdApartment()).get().getIdTenant(), user.get().getTenantUserId())){
                throw new Exception();
            }

        }catch (Exception e){
            reqRes.setStatusCode(400);
            return reqRes;

        }
        paymentsRepository.save(payment);


        try {
            Rent rent = rentRepository.findById(payment.getIdRent()).get();
            Integer amount = 0;
            for (Payment payment2 : paymentsRepository.findAll()) {
                if(payment2.getIdRent() == rent.getId())
                    amount += payment2.getAmount();
            }
            if(amount >= rent.getAmount()){
                rent.setPayed(true);
            }else {
                rent.setPayed(false);
            }
            rentRepository.save(rent);
        }
        catch(Exception e) {
            reqRes.setStatusCode(400);
            return reqRes;
        }

        reqRes.setStatusCode(200);
        return reqRes;
    }


    public ReqRes getPayments(){
        ReqRes reqRes = new ReqRes();
        LocalDate localDate = LocalDate.now(); // Get current date
        Optional<User> user = authService.getUser();

        List<RentsPayments> rentsPayments = new ArrayList<>();
        boolean foundSomething;
        try {
            for (Rent rent : rentRepository.findAll()) {
                if (rentAgreementRepository.findById(rent.getIdAgreement()).get().getIdTenant() != user.get().getTenantUserId()) {
                    continue;
                }
                List<Payment> payments1 = new ArrayList<>();
                RentsPayments rentPayments = new RentsPayments();
                rentPayments.setRent(rent);
                for (Payment payment : paymentsRepository.findAll()) {
                    if (rentAgreementRepository.findById(rent.getIdApartment()).get().getIdTenant() == user.get().getTenantUserId() && rent.getId() == payment.getIdRent()) {
                        payments1.add(payment);
                    }
                }
                rentPayments.setPayments(payments1);
                rentsPayments.add(rentPayments);
            }
        }catch(Exception e){
            reqRes.setStatusCode(400);
            return reqRes;
        }
        reqRes.setRPayments(rentsPayments);
        reqRes.setStatusCode(200);
        return reqRes;
    };


    public ReqRes getManagerPayments(){
        ReqRes reqRes = new ReqRes();
        LocalDate localDate = LocalDate.now(); // Get current date
        Optional<User> user = authService.getUser();

        List<RentsPayments> rentsPayments = new ArrayList<>();
        boolean foundSomething;
        try {
            for (Rent rent : rentRepository.findAll()) {
                if (rentAgreementRepository.findById(rent.getIdAgreement()).get().getIdManager() != user.get().getManagerUserId()) {
                    continue;
                }
                List<Payment> payments1 = new ArrayList<>();
                RentsPayments rentPayments = new RentsPayments();
                rentPayments.setRent(rent);
                for (Payment payment : paymentsRepository.findAll()) {
                    if (rentAgreementRepository.findById(rent.getIdApartment()).get().getIdManager() == user.get().getManagerUserId() && rent.getId() == payment.getIdRent()) {
                        payments1.add(payment);
                    }
                }
                rentPayments.setPayments(payments1);
                rentsPayments.add(rentPayments);
            }
        }catch(Exception e){
            reqRes.setStatusCode(400);
            return reqRes;
        }

        reqRes.setRPayments(rentsPayments);
        reqRes.setStatusCode(200);
        return reqRes;
    };


    public ReqRes deletePayment(Integer idPayment){
        ReqRes reqRes = new ReqRes();
        Optional<User> user = authService.getUser();

        try{
            Rent rent = rentRepository.findById(paymentsRepository.findById(idPayment).get().getIdRent()).get();
            Integer amount = 0;
            for (Payment payment : paymentsRepository.findAll()) {
                if(payment.getIdRent() == rent.getId())
                    amount += payment.getAmount();
            }
            amount -= paymentsRepository.findById(idPayment).get().getAmount();
            if(amount >= rent.getAmount()){
                rent.setPayed(true);
            }else{
                rent.setPayed(false);
            }
            rentRepository.save(rent);

            if(  rentAgreementRepository.findById((rentRepository.findById(paymentsRepository.findById(idPayment).get().getIdRent()).get().getIdAgreement())).get().getIdTenant() == user.get().getTenantUserId()){
                paymentsRepository.deleteById(idPayment);
            }else{
                throw new Exception();
            }

        }catch(Exception e){
            reqRes.setStatusCode(400);
            return reqRes;
        }

        reqRes.setStatusCode(200);
        return reqRes;
    }


}
