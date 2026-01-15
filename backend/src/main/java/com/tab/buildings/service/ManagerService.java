package com.tab.buildings.service;

import com.tab.buildings.entity.Apartment;
import com.tab.buildings.entity.Manager;
import com.tab.buildings.security.ReqRes;
import com.tab.buildings.wrapper.UserManager;

import java.util.List;

public interface ManagerService {

    /**
     * Method that adds new user of type Manager to database
     * @param userManager
     * @return
     * @throws Exception
     */
    ReqRes saveUserManager(UserManager userManager) throws Exception;

    List<Manager> fetchManagerList();

    // update operation
    Manager updateManager(Manager manager, Integer id_manager) throws Exception;

    /**
     * Method that returns buildings that have apartments of manager
     * @param manager_id
     * @return
     */
    public List<Apartment> getBuildingsByManagerId(int manager_id);

}
