package com.tab.buildings.service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tab.buildings.entity.Apartment;
import com.tab.buildings.entity.Building;
import com.tab.buildings.entity.Manager;
import com.tab.buildings.entity.User;
import com.tab.buildings.rep.ApartmentRepository;
import com.tab.buildings.rep.BuildingRepository;
import com.tab.buildings.rep.ManagerRepository;
import com.tab.buildings.rep.UserRepository;
import com.tab.buildings.security.AuthService;
import com.tab.buildings.security.ReqRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class BuildingServiceImpl implements BuildingService {
    @Autowired
    private BuildingRepository buildingRepository;
    @Autowired
    private ApartmentRepository apartmentRepository;
    @Autowired
    private AuthService authService;
    @Autowired
    private ManagerRepository managerRepository;
    @Autowired
    private UserRepository userRepository;


    // save operation
    @Override
    public ReqRes saveBuilding(Building Building) {
        ReqRes res = new ReqRes();
        try{
            buildingRepository.save(Building);
        }catch (Exception e){
            res.setStatusCode(400);
            return res;
        }
        res.setStatusCode(200);
        return res;

    }

    // read operation
    @Override
    public ReqRes fetchBuildings() {
        ReqRes reqRes = new ReqRes();
        Integer id = authService.getLoggedUser();
        Iterable<Apartment> apartments = apartmentRepository.findAll();
        Iterable<Apartment> apartmentsIterable = apartmentRepository.findAll();
        List<Apartment> apartmentsList = StreamSupport.stream(apartmentsIterable.spliterator(), false)
                .toList();
        List<Building> buildingsList = new ArrayList<>();

        for(Apartment apartment : apartments){

            if(apartment.getIdManager() == id){
                int buildingId = apartment.getId_building();
                Building building = buildingRepository.findById(buildingId).orElse(null);
                if(buildingsList.contains(building)){
                    building.setApartmentNumber(building.getApartmentNumber()+1);
                }else{
                    reqRes.setStatusCode(204);
                    buildingsList.add(building);
                    building.setApartments(null);
                    building.setApartmentNumber(building.getApartmentNumber()+1);
                }

            }
        }
        reqRes.setStatusCode(200);
        reqRes.setBuildings(buildingsList);
        return reqRes;

    }
    // update operation
    @Override
    public ReqRes updateBuilding(ObjectNode json) {

        Integer id = authService.getLoggedUser();

        int id_building = json.get("id_building").asInt();
        ReqRes reqRes = new ReqRes();

        // Create new if no id_building
        boolean edit = false;

        List<Building> buildings = (List<Building>) buildingRepository.findAll();
        for (Building building : buildings) {
            if(building.getId_building() == id_building){
                edit = true;
                break;
            }
        }
        Building building = new Building();
        building.setId_building(id_building);
        if(edit){
            // Authorization
            Building depDB = buildingRepository.findById(id_building).orElseThrow();
            List<Apartment> apartments = depDB.getApartments();
            boolean anyApartmentFound = false;
            for (Apartment apartment : apartments) {
                if(apartment.getId_building() == id_building && apartment.getIdManager() == id){
                    anyApartmentFound = true;
                    break;
                }
            }
            if(anyApartmentFound) {
                if (Objects.nonNull(json.get("address"))) {
                    building.setAddress(json.get("address").asText());
                }
                if (Objects.nonNull(json.get("city"))) {
                    building.setCity(json.get("city").asText());
                }
                if (Objects.nonNull(json.get("prowince"))) {
                    building.setProwince(json.get("prowince").asText());
                }
                buildingRepository.save(building);
                reqRes.setStatusCode(201);              // 201 jesli nie istanial
                return reqRes;
            }else {
                reqRes.setStatusCode(400);
                return reqRes;
            }
        }else{

            if (Objects.nonNull(json.get("address"))) {
                building.setAddress(json.get("address").asText());
            }
            if (Objects.nonNull(json.get("city"))) {
                building.setCity(json.get("city").asText());
            }
            if (Objects.nonNull(json.get("prowince"))) {
                building.setProwince(json.get("prowince").asText());
            }
            buildingRepository.save(building);
            reqRes.setStatusCode(200);
            return reqRes;
        }
    }

    // delete operation
    @Override
    public ReqRes deleteBuildingById(Integer BuildingId) {
        ReqRes reqRes = new ReqRes();
        try{
            buildingRepository.deleteById(BuildingId);
        }catch(Exception e) {
            reqRes.setStatusCode(400);
            return reqRes;
        }
        reqRes.setStatusCode(200);
        return reqRes;
    }

    @Override
    public ReqRes findById(Integer id_building) {
        ReqRes reqRes = new ReqRes();
        Optional<User> user;
        Optional<Manager> userEntity;
        List<Apartment> returnApartments = new ArrayList<>();
        user = authService.getUser();
        Iterable<Apartment> apartments = apartmentRepository.findAll();
        Iterable<Apartment> apartmentsIterable = apartmentRepository.findAll();
        List<Apartment> apartmentsList = StreamSupport.stream(apartmentsIterable.spliterator(), false)
                .toList();
        boolean found = false;

        try {
            userEntity = managerRepository.findById(user.get().getId());
            for (Apartment apartament : apartments) {
                if (Objects.equals(apartament.getId_building(), id_building) && Objects.equals(apartament.getIdManager(), user.get().getManagerUserId())) {
                    found = true;
                    break;
                }
            }
            if (found) {
                reqRes.setBuilding(buildingRepository.findById(id_building).orElseThrow());
                reqRes.setStatusCode(200);
                return reqRes;

            } else {
                reqRes.setStatusCode(403);
                return reqRes;
            }
        }
        catch(Exception e){
                reqRes.setStatusCode(404);
                return reqRes;

            }
    }
}

