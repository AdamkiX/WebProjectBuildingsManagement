package com.tab.buildings.service;

import com.tab.buildings.entity.Room;
import com.tab.buildings.security.ReqRes;

import java.util.List;

public interface RoomService{

    ReqRes saveRoom(Room room);

    ReqRes saveRooms(List<Room> rooms);
}
