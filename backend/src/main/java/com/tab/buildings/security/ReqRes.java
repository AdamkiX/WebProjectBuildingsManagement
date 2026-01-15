package com.tab.buildings.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.tab.buildings.entity.*;
import com.tab.buildings.wrapper.ApartmentTenant;
import com.tab.buildings.wrapper.ApartmentTenantRooms;
import com.tab.buildings.wrapper.RentsPayments;
import lombok.Data;
import org.springframework.boot.autoconfigure.info.ProjectInfoProperties;

import java.util.List;

/**
 * Big wrapper class
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReqRes {

    @JsonIgnore
    private int statusCode;

    private String error;
    private String message;
    @JsonIgnore
    private String token;
    @JsonIgnore
    private String refreshToken;
    @JsonIgnore
    private String expirationTime;
    private String username;
    private String password;
    private String userType;
    private User ourUsers;


    // USER DETAILS
    private Manager manager;
    private Tenant tenant;

    private List<Tenant> tenants;
    private List<Manager> managers;

    //GETTING STUFF
    private List<RentAgreement> rentAgreements;
    private List<Apartment> returnApartments;
    private List<RepairCom> repairComs;
    private List<Building> buildings;
    private List<Apartment> apartments;
    private List<Repair> repairs;
    private List<Rent> rents;
    private Rent rent;
    private Apartment apartment;
    private Building building;
    private List<ApartmentTenant> Tapartments;
    private List<ApartmentTenantRooms> TRapartments;
    private List<RentsPayments> RPayments;
    private List<RoomType> roomTypes;

}