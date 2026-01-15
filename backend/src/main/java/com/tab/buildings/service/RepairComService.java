package com.tab.buildings.service;

import com.tab.buildings.entity.RepairCom;
import com.tab.buildings.security.ReqRes;

public interface RepairComService {

    /**
     * Adds new repair commission to database
     * @param repairCom
     * @return
     */
    ReqRes saveCommission(RepairCom repairCom);

    /**
     * Returns all commissions associated with apartments that have relations with user
     * (ex. manager has apartment, tenant rents apartment)
     * @return
     */
    ReqRes getCommissions();
}
