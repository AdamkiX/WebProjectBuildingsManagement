package com.tab.buildings.controller;

import com.tab.buildings.entity.Repair;
import com.tab.buildings.security.ReqRes;
import com.tab.buildings.service.RepairService;
import com.tab.buildings.wrapper.RepairAdd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

@RestController
public class RepairController {
    private int statusCode;

    @Autowired
    private RepairService repairService;

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        return ResponseEntity.status(500).body(ex.getMessage());
    }

    /**
     * Manager's ability to add repair to repair commission associated with apartment
     * @param repair
     * @return
     */
    @PostMapping("/managers/repair")
    public ResponseEntity<Object> addRepair(@RequestBody RepairAdd repair) {
        ReqRes reqRes = repairService.saveRepair(repair);
        statusCode = reqRes.getStatusCode();
        return ResponseEntity.status(statusCode).body(ResponseEntity.ok(reqRes));
    }

    /**
     * Manager's ability to get history of all repairs in repair commission
     * @return
     */
    @GetMapping("/managers/repair")
    public ResponseEntity<Object> getRepairs() {
        ReqRes reqRes = repairService.getRepairs();
        statusCode = reqRes.getStatusCode();
        return ResponseEntity.status(statusCode).body(reqRes);
    }

    /**
     * Manager's ability to edit repair (ex. changing executor)
     * @param repair
     * @return
     */
    @PutMapping("/managers/repair")
    public ResponseEntity<Object> editRepair(@RequestBody Repair repair) {
        ReqRes reqRes = repairService.editRepair(repair, repair.getId());
        statusCode = reqRes.getStatusCode();
        return ResponseEntity.status(statusCode).body(reqRes);
    }
}
