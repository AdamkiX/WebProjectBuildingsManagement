package com.tab.buildings.service;

import com.tab.buildings.entity.Repair;;
import com.tab.buildings.security.ReqRes;
import com.tab.buildings.wrapper.RepairAdd;

// Only manager Service
public interface RepairService {

    /**
     * Adds new repair associated with repair commission
     * @param repair
     * @return
     */
    ReqRes saveRepair(RepairAdd repair);

    /**
     * Returns all repairs associated with repair commission
     * @return
     */
    ReqRes getRepairs();

    /**
     * Updates specific repair
     * @param repair
     * @param idRepair
     * @return
     */
    ReqRes editRepair(Repair repair, int idRepair);
}
