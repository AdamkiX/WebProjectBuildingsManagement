package com.tab.buildings.service;

import com.tab.buildings.entity.Room;
import com.tab.buildings.entity.RoomType;
import com.tab.buildings.rep.RoomTypeRepository;
import com.tab.buildings.security.ReqRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoomTypeServiceImpl implements RoomTypeService{

    @Autowired
    RoomTypeRepository roomTypeRepository;

    @Override
    public ReqRes getAllRoomTypes() {
        ReqRes reqRes = new ReqRes();
        try {
            Iterable<RoomType> tmp = roomTypeRepository.findAll();
            List<RoomType> roomTypes = new ArrayList<>();

            for (RoomType roomType : tmp) {
                roomTypes.add(roomType);
            }

            reqRes.setRoomTypes(roomTypes);
            reqRes.setMessage("Fetch was successful");
            reqRes.setStatusCode(200);

        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Internal server error " + e.getMessage());
            return reqRes;
        }

        return reqRes;
    }
}
