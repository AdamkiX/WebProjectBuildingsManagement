package com.tab.buildings.controller;

import com.tab.buildings.entity.Apartment;
import com.tab.buildings.entity.RepairCom;
import com.tab.buildings.security.ReqRes;
import com.tab.buildings.service.ApartmentService;
import com.tab.buildings.service.RepairComService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class RepairComController {

    private int statusCode;

    @Autowired
    private ApartmentService apartmentService;
    @Autowired
    private RepairComService repairComService;

    // Delete Commission form Apartment - manager set value in Apartment.id_repair_com to NULL
    /* JSON:
    {
        "id" : "apartmentId" <- deleting repair commission from that apartment
    }
    */

    /**
     * When called will get all commissions for user
     * @return
     */
    @GetMapping("/repaircom")
    public ResponseEntity<Object> getRepairComs() {
        ReqRes reqRes = repairComService.getCommissions();
        statusCode = reqRes.getStatusCode();
        return ResponseEntity.status(statusCode).body(reqRes);
    }

    /**
     * When called will create new reapir commission for apartment
     * @param repairCom
     * @return
     */
    @PostMapping("/repaircom")
    public ResponseEntity<Object> addRepairCom(@RequestBody RepairCom repairCom) {
        ReqRes reqRes = repairComService.saveCommission(repairCom);
        statusCode = reqRes.getStatusCode();
        return ResponseEntity.status(statusCode).body(reqRes);
    }

}
