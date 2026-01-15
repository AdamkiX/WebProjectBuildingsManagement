package com.tab.buildings.wrapper;

import com.tab.buildings.entity.Apartment;
import com.tab.buildings.entity.Room;

import java.util.List;

public class ApartmentRoom {
    private Apartment apartment;
    private List<Room> rooms;

    public ApartmentRoom (Apartment apartment, List<Room> rooms) {
        this.apartment = apartment;
        this.rooms = rooms;
    }

    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public Apartment getApartment() {
        return apartment;
    }

    public List<Room> getRooms() {
        return rooms;
    }
}
