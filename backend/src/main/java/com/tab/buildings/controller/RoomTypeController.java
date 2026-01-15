package com.tab.buildings.controller;

import com.tab.buildings.security.ReqRes;
import com.tab.buildings.service.RoomTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Controller for managing room types.
 */
@RestController
public class RoomTypeController {

    @Autowired
    RoomTypeService roomTypeService;

    /**
     * Gets all room types.
     * @return ResponseEntity with the status and response object.
     */
    @GetMapping("/managers/get_room_types")
    public ResponseEntity<Object> getRepairs() {
        ReqRes reqRes = roomTypeService.getAllRoomTypes();
        int statusCode = reqRes.getStatusCode();
        return ResponseEntity.status(statusCode).body(reqRes);
    }
}
