package com.tab.buildings.wrapper;

import com.tab.buildings.entity.*;

import java.util.ArrayList;
import java.util.List;

public class RentsPayments {
    private Rent rent;
    private List<Payment> payments;

    public RentsPayments() {
        this.payments = new ArrayList<>();
        this.rent = new Rent();
    }

    public Rent getRent() {return rent;}
    public void setRent(Rent rent) {this.rent = rent;}
    public List<Payment> getPayments() {return this.payments;}
    public void setPayments (List<Payment> payments) {this.payments = payments;}

}

