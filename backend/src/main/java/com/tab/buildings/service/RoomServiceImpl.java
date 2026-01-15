package com.tab.buildings.service;

import com.tab.buildings.entity.Room;
import com.tab.buildings.entity.RoomId;
import com.tab.buildings.rep.RoomRepository;
import com.tab.buildings.security.ReqRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class RoomServiceImpl implements RoomService{

    @Autowired
    private RoomRepository roomRepository;
    private boolean isList = false;
    private boolean breakLoop = false;

    @Override
    public ReqRes saveRoom(Room room) {
        Iterable<Room> allRooms = roomRepository.findAll();
        ReqRes reqRes = new ReqRes();
        try {
            RoomId roomId = room.getId();
            if (roomId == null || roomId.getIdApartment() == null || roomId.getIdRoom() == null) {
                reqRes.setMessage("Room ID and Apartment ID must be provided and non-null.");
                reqRes.setStatusCode(400);
                breakLoop = true;
                return reqRes;
            }
            for (Room room1 : allRooms) {
                if (Objects.equals(room1.getId().getIdApartment(), roomId.getIdApartment()) && Objects.equals(room1.getId().getIdRoom(), roomId.getIdRoom())) {
                    reqRes.setMessage("You have already declared those type of rooms in this apartment");
                    reqRes.setStatusCode(403);
                    breakLoop = true;
                    return reqRes;
                }
            }
            int roomCount = room.getRoomCount();
            roomRepository.save(room);
        } catch (NumberFormatException n) {
            if (isList) {
                breakLoop = true;
            }
            reqRes.setMessage("Provided room count: " + room.getRoomCount() + " is not valid. Field should not be empty or have special/alphabetical characters.");
            reqRes.setStatusCode(400);
            return reqRes;
        } catch (IllegalArgumentException e) {
            if (isList) {
                breakLoop = true;
            }
            reqRes.setStatusCode(400);
            reqRes.setMessage(e.getMessage());
            return reqRes;
        } catch (Exception e) {
            if (isList) {
                breakLoop = true;
            }
            reqRes.setStatusCode(500);
            reqRes.setMessage("Internal server error: " + e.getMessage());
            return reqRes;
        }
        if (!isList) {
            reqRes.setMessage("You've successfully added a room.");
            reqRes.setStatusCode(200);
        }
        return reqRes;
    }

    @Override
    public ReqRes saveRooms(List<Room> rooms) {
        isList = true;
        ReqRes reqRes = new ReqRes();
        ReqRes a = new ReqRes();

        for (Room room : rooms) {
            if (!breakLoop) {
                a = saveRoom(room);
            } else {
                return a;
            }
        }

        isList = false;
        reqRes.setStatusCode(200);
        reqRes.setMessage("You've successfully added rooms to your apartment.");
        return reqRes;
    }

}
