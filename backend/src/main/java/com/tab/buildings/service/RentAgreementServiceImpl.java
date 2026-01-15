package com.tab.buildings.service;

import com.tab.buildings.entity.*;
import com.tab.buildings.rep.*;
import com.tab.buildings.security.AuthService;
import com.tab.buildings.security.ReqRes;
import com.tab.buildings.wrapper.ApartmentTenant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.Date;
import java.time.LocalDate;

import java.util.*;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
public class RentAgreementServiceImpl implements RentAgreementService {

    @Autowired
    private RentAgreementRepository rentAgreementRepository;
    @Autowired
    private AuthService authService;
    @Autowired
    private TenantRepository tenantRepository;
    @Autowired
    private ApartmentRepository apartmentRepository;
    @Autowired
    private ManagerRepository managerRepository;
    @Autowired
    private RentRepository rentRepository;

    //Add Agreement
    @Override
    public ReqRes saveAgreement(RentAgreement request) {
        ReqRes reqRes = new ReqRes();

        try {

            Optional<Tenant> tenant;
            Integer id = authService.getLoggedUser();
            Apartment apartment = null;
            Iterable<RentAgreement> rentAgreements = rentAgreementRepository.findAll();

            try {
                apartment = apartmentRepository.findById(request.getIdApartment()).get();
                int check = apartment.getId();
            } catch (NullPointerException e)
            {
                reqRes.setStatusCode(404);
                reqRes.setMessage("No apartment found. Please Check if you have parsed correct one.");
                return reqRes;
            }

            if (!Objects.equals(apartment.getIdManager(), id)) {
                reqRes.setStatusCode(403);
                reqRes.setMessage("This is not your apartment. You can not make rent agreement because of that.");
                return reqRes;
            }

            for (RentAgreement rentAgreement : rentAgreements) {
                if (Objects.equals(rentAgreement.getIdApartment(), request.getIdApartment())
                        && Objects.equals(rentAgreement.getStatus(), "active")) {
                    reqRes.setStatusCode(403);
                    reqRes.setMessage("Tenant has already rent agreement with you on this apartment. You can not add second one.");
                    return reqRes;
                }
            }

            tenant = tenantRepository.findById(request.getIdTenant());
            request.setIdManager(id);
            rentAgreementRepository.save(request);

            reqRes.setStatusCode(200);
            reqRes.setMessage("You've successfully sign agreement with " + tenant.get().getName() + " " + tenant.get().getSurname());

            return reqRes;
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Rent agreement could not be saved. Please check if user exists or other issues.");
            return reqRes;
        }
    };

    //Get AllUserAgreements
    @Override
    public ReqRes getAllAgreements() {
        ReqRes reqRes = new ReqRes();

        List <Manager> managersData = new ArrayList<>();
        List <Tenant> tenantsData = new ArrayList<>();
        List <Apartment> apartmentsData = new ArrayList<>();

        String userType = authService.loggedUserType();
        Integer id = authService.getLoggedUser();
        Iterable<RentAgreement> rentAgreementsIt = rentAgreementRepository.findAll();
        List<RentAgreement> agreements = StreamSupport.stream(rentAgreementsIt.spliterator(), false).toList();
        List<RentAgreement> myAgreements = new ArrayList<>();

        if(userType.equals("manager")) {

            for (RentAgreement agreement : agreements) {

                if (Objects.equals(agreement.getIdManager(), id)) {
                    Tenant tenant = tenantRepository.findById(agreement.getIdTenant()).get();
                    tenantsData.add(tenant);
                    Apartment apartment = apartmentRepository.findById(agreement.getIdApartment()).get();
                    apartmentsData.add(apartment);
                    myAgreements.add(agreement);
                }
            }
        } else {
            for (RentAgreement agreement : agreements) {

                if (Objects.equals(agreement.getIdTenant(), id)) {
                    Manager manager = managerRepository.findById(agreement.getIdManager()).get();
                    managersData.add(manager);
                    Apartment apartment = apartmentRepository.findById(agreement.getIdApartment()).get();
                    apartmentsData.add(apartment);
                    myAgreements.add(agreement);
                }
            }
        }

        if(myAgreements.isEmpty()){
            reqRes.setMessage("You've no rent agreements.");
        }

        reqRes.setStatusCode(200);
        reqRes.setRentAgreements(myAgreements);
        reqRes.setApartments(apartmentsData);
        if(userType.equals("manager")) {
            reqRes.setTenants(tenantsData);
        } else {
            reqRes.setManagers(managersData);
        }

        return reqRes;
    }

    @Override
    public ReqRes editAgreement(RentAgreement rentAgreement){
        ReqRes reqRes = new ReqRes();
        Optional<Apartment> apartment;

        apartment = apartmentRepository.findById(rentAgreement.getIdApartment());

        // Authorization
        RentAgreement depDB = rentAgreementRepository.findById(rentAgreement.getId()).orElseThrow();
        try {
            if (Objects.nonNull(rentAgreement.getAgrDate())) {
                depDB.setAgrDate(rentAgreement.getAgrDate());
            }
            if (Objects.nonNull(rentAgreement.getRentStartDate())) {
                depDB.setRentStartDate(rentAgreement.getRentStartDate());
            }
            if (Objects.nonNull(rentAgreement.getRentEndDate())) {
                depDB.setRentEndDate(rentAgreement.getRentEndDate());
            }
            if (Objects.nonNull(rentAgreement.getRentPrice())) {
                depDB.setRentPrice(rentAgreement.getRentPrice());
            }
            if (Objects.nonNull(rentAgreement.getIdTenant())) {
                depDB.setIdTenant(rentAgreement.getIdTenant());
            }
            if (Objects.nonNull(rentAgreement.getIdApartment()) && (apartment.get().getIdManager() == rentAgreement.getIdManager())) {
                depDB.setIdApartment(rentAgreement.getIdApartment());
            }
            if (Objects.nonNull(rentAgreement.getStatus())) {
                depDB.setStatus(rentAgreement.getStatus());
            }
            rentAgreementRepository.save(depDB);
            reqRes.setStatusCode(200);
            reqRes.setMessage("Successfully edited agreement ");
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage(e.getMessage());
        }
        return reqRes;
    }

    public ReqRes generateRents(){

        ReqRes reqRes = new ReqRes();
        Optional<User> user;
        user = authService.getUser();
        Optional<Manager> userEntity = managerRepository.findById(user.get().getManagerUserId());
        LocalDate localDate = LocalDate.now(); // Get current date

        try{
            for(RentAgreement rentAgreement : rentAgreementRepository.findAll()){
               Date lastDate;
               lastDate = rentAgreement.getRentStartDate();
               // find if rentAgreement yours
               if(Objects.equals(rentAgreement.getIdManager(), user.get().getManagerUserId())){
                   // find last rent
                   for(Rent rent : rentRepository.findAll()){
                       if(Objects.equals(rent.getIdAgreement(), rentAgreement.getId())){    // your correct rent
                           Date getDate = rent.getEndDate();
                           if(getDate.after(lastDate)){
                                lastDate = getDate; // new last from this agreement
                            }
                       }
                   }
                   // Generate rents for each missing month between lastDate and sqlDate
                   LocalDate startLocalDate = lastDate.toLocalDate();
                   while (true) {
                       // Generate rent for date
                       if(startLocalDate.isAfter(localDate)){
                           break;
                       }
                        Integer zero = 0;
                        rentRepository.save(new Rent(-1,Date.valueOf(localDate),Date.valueOf(startLocalDate.plusMonths(1)),Date.valueOf(startLocalDate),"standard rent",rentAgreement.getRentPrice(),false,rentAgreement.getIdApartment(),rentAgreement.getId()));
                        startLocalDate = startLocalDate.plusMonths(1);
                   }
               }
            }
        }catch (Exception e){
            reqRes.setStatusCode(500);
            return
                    reqRes;
        }
        reqRes.setStatusCode(200);
        return
                reqRes;

    };


    public ReqRes getRents(Integer agreement_id){
        ReqRes reqRes = new ReqRes();
        Optional<User> user;
        user = authService.getUser();
        Optional<Manager> userEntity = managerRepository.findById(user.get().getManagerUserId());
        List<Rent> returnRents = new ArrayList<>();

        try{
            for(RentAgreement rentAgreement : rentAgreementRepository.findAll()){
                Date lastDate;
                lastDate = rentAgreement.getRentStartDate();
                // find if rentAgreement yours
                if(Objects.equals(rentAgreement.getIdManager(), user.get().getManagerUserId()) && Objects.equals(agreement_id, rentAgreement.getId())){
                    // find last rent
                    for(Rent rent : rentRepository.findAll()){
                        if(Objects.equals(rent.getIdAgreement(), rentAgreement.getId())){    // your correct rent
                            returnRents.add(rent);
                        }
                    }
                }
            }
            reqRes.setRents(returnRents);
            reqRes.setStatusCode(200);
            return reqRes;
        }catch (Exception e){
            reqRes.setStatusCode(500);
            return reqRes;

        }

    };
}
