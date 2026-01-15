package com.tab.buildings.wrapper;

import com.tab.buildings.entity.Manager;
import com.tab.buildings.entity.User;

public class UserManager {
    private User user;
    private Manager manager;

    public UserManager(User user, Manager manager) {
        this.user = user;
        this.manager = manager;
    }

    public User getUser() {return user;}
    public Manager getManager() {return manager;}

    public void setUser(User user) {this.user = user;}
    public void setManager(Manager manager) {this.manager = manager;}
}

