package com.tab.buildings.controller;

import com.tab.buildings.entity.Room;
import com.tab.buildings.security.ReqRes;
import com.tab.buildings.service.RoomService;
import com.tab.buildings.wrapper.RepairAdd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller for managing rooms.
 */
@RestController
public class RoomController {

    int statusCode;

    @Autowired
    RoomService roomService;
    /**
     * Adds a new room.
     * @param room The room to be added.
     * @return ResponseEntity with the status and response object.
     */
    @PostMapping("/managers/add_room")
    public ResponseEntity<Object> addRepair(@RequestBody Room room) {
        ReqRes reqRes = roomService.saveRoom(room);
        statusCode = reqRes.getStatusCode();
        return ResponseEntity.status(statusCode).body(ResponseEntity.ok(reqRes));
    }
    /**
     * Adds multiple new rooms.
     * @param rooms The list of rooms to be added.
     * @return ResponseEntity with the status and response object.
     */
    @PostMapping("/managers/add_rooms")
    public ResponseEntity<Object> addRepair(@RequestBody List<Room> rooms) {
        ReqRes reqRes = roomService.saveRooms(rooms);
        statusCode = reqRes.getStatusCode();
        return ResponseEntity.status(statusCode).body(ResponseEntity.ok(reqRes));
    }
}
