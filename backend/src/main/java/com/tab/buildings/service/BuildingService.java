package com.tab.buildings.service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tab.buildings.entity.Building;
import com.tab.buildings.security.ReqRes;

public interface BuildingService {
    // save operation
    ReqRes saveBuilding(Building building);

    // read operation by manager_id
    ReqRes fetchBuildings();


    // update operation
    ReqRes updateBuilding(ObjectNode json);

    // delete operation
    ReqRes deleteBuildingById(Integer buildingId);

    ReqRes findById(Integer id_building);
}
