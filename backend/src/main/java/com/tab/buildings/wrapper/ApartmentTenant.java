package com.tab.buildings.wrapper;

import com.tab.buildings.entity.Apartment;
import com.tab.buildings.entity.Manager;
import com.tab.buildings.entity.Tenant;
import com.tab.buildings.entity.User;

public class ApartmentTenant {
    private Apartment apartment;
    private Tenant tenant;

    public ApartmentTenant(Apartment apartment, Tenant tenant) {
        this.apartment = apartment;
        this.tenant = tenant;
    }

    public Apartment getApartment() {return apartment;}
    public Tenant getTenant() {return tenant;}

    public void setApartment(Apartment apartment) { this.apartment = apartment;}
    public void setTenant(Tenant tenant) { this.tenant = tenant;}
}

