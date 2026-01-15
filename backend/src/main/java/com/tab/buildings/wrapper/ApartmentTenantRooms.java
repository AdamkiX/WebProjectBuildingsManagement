package com.tab.buildings.wrapper;

import com.tab.buildings.entity.Apartment;
import com.tab.buildings.entity.Room;
import com.tab.buildings.entity.Tenant;

import java.util.ArrayList;
import java.util.List;

public class ApartmentTenantRooms {
    private Apartment apartment;
    private Tenant tenant;
    private List<Room> rooms;

    public ApartmentTenantRooms(Apartment apartment, Tenant tenant, List<Room> rooms) {
        this.apartment = apartment;
        this.tenant = tenant;
        this.rooms = new ArrayList<>();
    }

    public Apartment getApartment() {return apartment;}
    public Tenant getTenant() {return tenant;}
    public List<Room> getRooms() {return rooms;}

    public void setApartment(Apartment apartment) { this.apartment = apartment;}
    public void setTenant(Tenant tenant) { this.tenant = tenant;}
    public void setRooms(List<Room> rooms) { this.rooms = rooms;}
}

