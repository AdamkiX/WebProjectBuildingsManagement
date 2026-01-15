package com.tab.buildings.service;

import com.tab.buildings.entity.*;
import com.tab.buildings.rep.*;
import com.tab.buildings.security.AuthService;
import com.tab.buildings.security.ReqRes;
import com.tab.buildings.wrapper.ApartmentTenant;
import com.tab.buildings.wrapper.ApartmentTenantRooms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class ApartmentServiceImpl implements ApartmentService {
    @Autowired
    private ApartmentRepository apartmentRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private ManagerRepository managerRepository;
    @Autowired
    private BuildingRepository buildingsRepository;
    @Autowired
    private AuthService authService;
    @Autowired
    private RentAgreementRepository rentAgreementRepository;
    @Autowired
    private TenantRepository tenantRepository;

    // Save operation, always adds new apartment
    @Override
    public ReqRes saveApartment(Apartment apartment) {
        ReqRes reqRes = new ReqRes();
        Optional<User> user = Optional.empty();
        user = authService.getUser();
        try {
            Integer id_manager = user.get().getManagerUserId();
            apartment.setId(Integer.MAX_VALUE);
            apartment.setIdManager(user.get().getId());
            apartmentRepository.save(apartment);
            reqRes.setStatusCode(200);
            reqRes.setMessage("Zapisano apartment w bazie");
            return reqRes;
        } catch (Exception e) {

            reqRes.setStatusCode(500);
            reqRes.setMessage("Straszny blad weryfikacji");
            return reqRes;
        }
    }

    // Update operation
    @Override
    public ReqRes updateApartment(Apartment apartment, Integer apartmentId) {
        Optional<Apartment> apartmentOpt = apartmentRepository.findById(apartmentId);
        ReqRes reqRes = new ReqRes();
        if (apartmentOpt.isPresent()) {
            Apartment existingApartment = apartmentOpt.get();

            if (Objects.nonNull(apartment.getApt_number())) {
                existingApartment.setApt_number(apartment.getApt_number());
            }

            if (Objects.nonNull(apartment.getLevel())) {
                existingApartment.setLevel(apartment.getLevel());
            }

            if (Objects.nonNull(apartment.getApt_area())) {
                existingApartment.setApt_area(apartment.getApt_area());
            }

            if (Objects.nonNull(apartment.getDetails())) {
                existingApartment.setDetails(apartment.getDetails());
            }

            if (Objects.nonNull(apartment.getIdTenant())) {
                existingApartment.setIdTenant(apartment.getIdTenant());
            }
            apartmentRepository.save(existingApartment);
            reqRes.setStatusCode(200);
            return reqRes;

        } else {
            reqRes.setStatusCode(400);
            return reqRes;
        }
    }

    // Delete operation
    @Override
    public ReqRes deleteApartmentById(Integer apartmentId) {
        Apartment apartment;
        ReqRes reqRes = new ReqRes();
        try{
            apartment = apartmentRepository.findById(apartmentId).orElseThrow();
        }catch (Exception e) {
            reqRes.setStatusCode(404);
            return reqRes;
        }
        apartmentRepository.deleteById(apartmentId);
        reqRes.setStatusCode(200);
        return reqRes;
    }

    ////fhfgh
    public List<Apartment> findByManagerId(int managerId) {
        Manager manager = managerRepository.findById(managerId)
                .orElse(null); // orElse(null) returns null if Optional is empty

        if (manager != null) {
            return apartmentRepository.findByIdManager(manager);
        } else {
            // Handle case where manager with given ID is not found
            return Collections.emptyList(); // Or throw an exception, or handle it according to your requirements
        }
    }


    public ReqRes findApartmentsByBuildingId(Integer buildingId) {

        Iterable<Apartment> apartments = apartmentRepository.findAll();
        Iterable<Tenant> tenants = tenantRepository.findAll();
        ReqRes response = new ReqRes();
        Optional<Manager> managerEntity;
        Optional<User> user;
        Optional<Manager> userEntity;
        List<ApartmentTenant> returnApartments = new ArrayList<>();
        user = authService.getUser();
        boolean foundTenant = false;

        try {
            userEntity = managerRepository.findById(user.get().getManagerUserId());
            for (Apartment apartament : apartments) {
                if (Objects.equals(apartament.getId_building(), buildingId) && Objects.equals(apartament.getIdManager(), user.get().getManagerUserId())) {
                    // get dane najemcy
                    Tenant tenant = new Tenant();
                    tenant.setId(-1);
                    ApartmentTenant returnApartment = new ApartmentTenant(apartament, tenant);
                    returnApartment.setTenant(tenant);
                    Integer idApartmentTenant;

                    try{
                        if(apartament.getIdTenant() == null)
                            throw new RuntimeException("no tenant");
                        idApartmentTenant = apartament.getIdTenant();
                        for(Tenant tenant1 : tenants) {
                            if(tenant1.getId() == idApartmentTenant) {
                                returnApartment.setTenant(tenant1);
                                foundTenant = true;
                                break;
                            }
                        }
                    }catch (Exception e) {
                        response.setStatusCode(500);
                        response.setMessage(e.getMessage());
                    }

                    returnApartments.add(returnApartment);
                }
            }

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
        }
        if(foundTenant) {
            response.setStatusCode(200);
            response.setTapartments(returnApartments);
            return response;
        }else{
            response.setStatusCode(206);
            response.setTapartments(returnApartments);
            return response;

        }

    }

    public ReqRes findApartmentsWithRoomsByBuildingId(Integer buildingId) {

        Iterable<Apartment> apartments = apartmentRepository.findAll();
        Iterable<Tenant> tenants = tenantRepository.findAll();
        Iterable<Room> rooms = roomRepository.findAll();
        ReqRes response = new ReqRes();
        Optional<Manager> managerEntity;
        Optional<User> user;
        Optional<Manager> userEntity;
        List<ApartmentTenantRooms> returnApartments = new ArrayList<>();
        user = authService.getUser();
        boolean foundTenant = false;
        boolean noApartments = true;

        try {
            userEntity = managerRepository.findById(user.get().getManagerUserId());
            for (Apartment apartment : apartments) {
                if (Objects.equals(apartment.getId_building(), buildingId) && Objects.equals(apartment.getIdManager(), user.get().getManagerUserId())) {
                    noApartments = false;
                    // get dane najemcy
                    Tenant tenant = new Tenant();
                    tenant.setId(-1);
                    List<Room> roomsList = new ArrayList<>();
                    ApartmentTenantRooms returnApartment = new ApartmentTenantRooms(apartment, tenant,roomsList);
                    returnApartment.setTenant(tenant);
                    Integer idApartmentTenant;

                    try{
                        if(apartment.getIdTenant() == null)
                            throw new RuntimeException("no tenant");
                        idApartmentTenant = apartment.getIdTenant();
                        for(Tenant tenant1 : tenants) {
                            if(tenant1.getId() == idApartmentTenant) {
                                returnApartment.setTenant(tenant1);
                                foundTenant = true;
                                break;
                            }
                        }
                    }catch (Exception e) {
                        response.setStatusCode(500);
                        response.setMessage(e.getMessage());
                    }
                    //get rooms
                    for(Room room : rooms){
                        RoomId roomId = room.getId();
                        if(Objects.equals(apartment.getId(), roomId.getIdApartment())){
                            roomsList.add(room);
                        }
                    returnApartment.setRooms(roomsList);
                    }
                    returnApartments.add(returnApartment);
                }
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
        }
        if(noApartments){
            response.setStatusCode(204);
            response.setTRapartments(returnApartments);
            return response;

        }
        if(foundTenant) {
            response.setStatusCode(200);
            response.setTRapartments(returnApartments);
            return response;
        }else{
            response.setStatusCode(206);
            response.setTRapartments(returnApartments);
            return response;
        }
    }

    public ReqRes fetchApartment(@PathVariable("id") Integer id_apartment){
        ReqRes reqRes = new ReqRes();
        Apartment apartment;
        Integer id = authService.getLoggedUser();
        Optional<User> user;
        user = authService.getUser();

        try {
            apartment = apartmentRepository.findById(id_apartment).orElseThrow();
            try{
                if (!Objects.equals(apartment.getIdManager(), user.get().getManagerUserId()) && !Objects.equals(apartment.getIdTenant(), user.get().getManagerUserId())) {
                    throw new Exception("not your apartment");
                }
            }catch(Exception e){
                reqRes.setStatusCode(403);
                return reqRes;
            }
        }catch (Exception e){
            reqRes.setStatusCode(400);
            return reqRes;
        }
        reqRes.setApartment(apartment);
        reqRes.setStatusCode(200);
        return reqRes;
    }

    @Override
    public ReqRes deleteTenantFromApartment(Integer apartmentId) {
       ReqRes reqRes = new ReqRes();
       User user = authService.getUser().get();
       Iterable<RentAgreement> rentAgreements = rentAgreementRepository.findAll();
       Tenant tenant = null;

       Apartment apartment = apartmentRepository.findById(apartmentId).get();
       if (!Objects.equals(apartment.getIdManager(), user.getManagerUserId())){
           reqRes.setStatusCode(403);
           reqRes.setMessage("This is not your apartment. You can not delete tenant from there!");
           return reqRes;
       }

       // finding rent agreement that has in apartment the same tenant as in rent agreement
       for (RentAgreement rentAgreement : rentAgreements) {
           if (Objects.equals(rentAgreement.getIdApartment(), apartmentId)) {
               if (Objects.equals(apartment.getIdTenant(), rentAgreement.getIdTenant())) {
                   DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                   LocalDate rentEnd = rentAgreement.getRentEndDate().toLocalDate();
                   LocalDate today = LocalDate.now();
                   if (rentEnd.isBefore(today) && !Objects.equals(rentAgreement.getStatus(), "terminated")) {
                       reqRes.setStatusCode(403);
                       reqRes.setMessage("You cannot delete tenant from apartment. Rent agreement didn't ended yet.");
                       return reqRes;
                   }
                   tenant = tenantRepository.findById(rentAgreement.getIdTenant()).get();
                   break;
               }
           }
       }

        try {
            reqRes.setMessage("You successfully deleted tenant: " + tenant.getName() + " " + tenant.getSurname() + ".");
        } catch (Exception e) {
           reqRes.setStatusCode(500);
           reqRes.setMessage(e.getMessage());
        }
        apartment.setIdTenant(null);
        reqRes.setStatusCode(200);
        return reqRes;
    }

    @Override
    public ReqRes addTenant(Integer idApartment, Integer idTenant) {
        ReqRes reqRes = new ReqRes();
        try {
            Apartment apartment = apartmentRepository.findById(idApartment).get();
            apartment.setIdTenant(idTenant);
            apartmentRepository.save(apartment);
            reqRes.setStatusCode(200);
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Something broke and didn't want to work. " + e.getMessage());
        }
        return reqRes;
    }


    @Override
    public ReqRes findAllTenantApartments(){
        ReqRes reqRes = new ReqRes();

        Iterable<Apartment> apartments = apartmentRepository.findAll();
        Iterable<Tenant> tenants = tenantRepository.findAll();
        Iterable<Room> rooms = roomRepository.findAll();
        ReqRes response = new ReqRes();
        Optional<User> user;
        Optional<Manager> userEntity;
        List<ApartmentTenantRooms> returnApartments = new ArrayList<>();
        user = authService.getUser();
        boolean foundTenant = false;
        boolean noApartments = true;

        try {
            for (Apartment apartment : apartments) {
                if (Objects.equals(apartment.getIdTenant(), user.get().getTenantUserId())) {
                    noApartments = false;
                    // get dane najemcy
                    Tenant tenant = new Tenant();
                    tenant.setId(-1);
                    List<Room> roomsList = new ArrayList<>();
                    ApartmentTenantRooms returnApartment = new ApartmentTenantRooms(apartment, tenant,roomsList);
                    returnApartment.setTenant(tenant);
                    Integer idApartmentTenant;

                    try{
                        if(apartment.getIdTenant() == null)
                            throw new RuntimeException("no tenant");
                        idApartmentTenant = apartment.getIdTenant();
                        for(Tenant tenant1 : tenants) {
                            if(tenant1.getId() == idApartmentTenant) {
                                returnApartment.setTenant(tenant1);
                                foundTenant = true;
                                break;
                            }
                        }
                    }catch (Exception e) {
                        response.setStatusCode(500);
                        response.setMessage(e.getMessage());
                    }
                    //get rooms
                    for(Room room : rooms){
                        RoomId roomId = room.getId();
                        if(Objects.equals(apartment.getId(), roomId.getIdApartment())){
                            roomsList.add(room);
                        }
                        returnApartment.setRooms(roomsList);
                    }
                    returnApartments.add(returnApartment);
                }
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
        }
        if(noApartments){
            response.setStatusCode(204);
            response.setTRapartments(returnApartments);
            return response;

        }
        if(foundTenant) {
            response.setStatusCode(200);
            response.setTRapartments(returnApartments);
            return response;
        }else{
            response.setStatusCode(206);
            response.setTRapartments(returnApartments);
            return response;
        }
    };




}