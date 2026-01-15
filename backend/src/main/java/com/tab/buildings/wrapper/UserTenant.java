package com.tab.buildings.wrapper;

import com.tab.buildings.entity.Tenant;
import com.tab.buildings.entity.User;

public class UserTenant {
    private User user;
    private Tenant tenant;

    public UserTenant(User user, Tenant tenant) {
        this.user = user;
        this.tenant = tenant;
    }

    public User getUser() {return user;}
    public Tenant getTenant() {return tenant;}

    public void setUser(User user) {this.user = user;}
    public void setTenant(Tenant tenant) {this.tenant = tenant;}
}
