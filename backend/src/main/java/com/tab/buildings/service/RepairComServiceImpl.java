package com.tab.buildings.service;

import com.tab.buildings.entity.RentAgreement;
import com.tab.buildings.entity.RepairCom;
import com.tab.buildings.entity.User;
import com.tab.buildings.entity.Apartment;
import com.tab.buildings.rep.ApartmentRepository;
import com.tab.buildings.rep.RentAgreementRepository;
import com.tab.buildings.rep.RepairComRepository;
import com.tab.buildings.security.AuthService;
import com.tab.buildings.security.ReqRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class RepairComServiceImpl implements RepairComService {

    @Autowired
    private RepairComRepository repairComRepository;
    @Autowired
    private AuthService authService;
    @Autowired
    private ApartmentRepository apartmentRepository;
    @Autowired
    private RentAgreementRepository rentAgreementRepository;

    // Add repair_com
    @Override
    public ReqRes saveCommission(RepairCom repairCom) {
        ReqRes reqRes = new ReqRes();
        boolean ok = false;
        //For Manager: Check if apartment is owned by user
        //For Tenant: Check if apartment is on rent agreement list

        Iterable<Apartment> apartments = apartmentRepository.findAll();
        Iterable<RentAgreement> rentAgreements = rentAgreementRepository.findAll();
        User user = authService.getUser().get();
        if (Objects.equals(user.getUsertype(), "manager")) {
            for (Apartment apartment : apartments) {
                if (Objects.equals(apartment.getIdManager(), user.getManagerUserId())) {
                    ok = true;
                    break;
                }
            }
            if (!ok) {
                reqRes.setStatusCode(403);
                reqRes.setMessage("You cannot add repair commission, because you do not own this apartment");
                return reqRes;
            }
        } else {
           for (RentAgreement rentAgreement : rentAgreements) {
               if (Objects.equals(rentAgreement.getIdTenant(), user.getTenantUserId())) {
                   ok = true;
                   break;
               }
           }
            if (!ok) {
                reqRes.setStatusCode(403);
                reqRes.setMessage("You cannot add repair commission, because you do not have any rent agreement related to that apartment");
                return reqRes;
            }
        }

        try {
            if(repairCom.getTitle().isEmpty()){
                reqRes.setStatusCode(400);
                reqRes.setMessage("Please fill your commission title.");
                return reqRes;
            }
            if(repairCom.getDescription().isEmpty()){
                reqRes.setStatusCode(400);
                reqRes.setMessage("Please fill your commission description.");
                return reqRes;
            }
            LocalDate today = LocalDate.now();
            repairCom.setCreationDate(today.toString());
            repairCom.setStatus("pending");

            repairComRepository.save(repairCom);

            reqRes.setStatusCode(200);
            reqRes.setMessage("Successfully saved commission. Please wait until it's resolved by your manager.");
        } catch (Exception e) { //Other unknown exceptions
            reqRes.setStatusCode(500);
            reqRes.setMessage(e.getMessage());
        }
        return reqRes;
    }

    @Override
    public  ReqRes getCommissions() {
        ReqRes reqRes = new ReqRes();
        User user = authService.getUser().get();

        Iterable<RepairCom> repairComs = repairComRepository.findAll();
        List<RepairCom> myRepairComs = new ArrayList<>();
        List<Apartment> myApartments = new ArrayList<>();

        //There will be 2 checks:
            //Tenant where you check rent_agreement for apartments
            //Then check apartments for repair_com_id
            //Then get repair_com byId and add to some list and return it

            //Manager will get all his apartments from database and if id_repair_com exists get that id_repair with apartment location and return it

        if (user.getUsertype().equals("manager")) { //Manager
            for (RepairCom repairCom : repairComs) {
               if (Objects.equals(apartmentRepository.findById(repairCom.getIdApartment()).get().getIdManager(), user.getManagerUserId())) {
                   myApartments.add(apartmentRepository.findById(repairCom.getIdApartment()).get());
                   myRepairComs.add(repairCom);
               }
            }
        } else { //Tenant
            for (RepairCom repairCom : repairComs) {
                if (repairCom.getIdRentAgreement() != null) {
                    if (Objects.equals(rentAgreementRepository.findById(repairCom.getIdRentAgreement()).get().getIdTenant(), user.getTenantUserId())) {
                        myApartments.add(apartmentRepository.findById(repairCom.getIdApartment()).get());
                        myRepairComs.add(repairCom);
                    }
                }
            }
        }

        reqRes.setStatusCode(200);
        reqRes.setMessage("Fetch was successful.");

        if(!myRepairComs.isEmpty()){
            reqRes.setRepairComs(myRepairComs);
            reqRes.setApartments(myApartments);
        } else {
            reqRes.setMessage(reqRes.getMessage() + " There is no repair commissions for any of your apartments.");
        }

        return reqRes;
    }
}
