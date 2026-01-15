package com.tab.buildings.entity;
import jakarta.persistence.Embeddable;

import java.io.Serializable;


/**
 * Embeddable class representing the composite primary key for the Room entity.
 */
@Embeddable
public class RoomId implements Serializable {

    /**
     * Identifier of the room.
     */
    private Integer idRoom;

    /**
     * Identifier of the apartment.
     */
    private Integer idApartment;

    /**
     * Default constructor.
     */
    public RoomId() {}

    /**
     * Constructor with parameters.
     * @param idRoom The identifier of the room.
     * @param idApartment The identifier of the apartment.
     */
    public RoomId(Integer idRoom, Integer idApartment) {
        this.idApartment = idApartment;
        this.idRoom = idRoom;
    }

    /**
     * Getter for idApartment.
     * @return The identifier of the apartment.
     */
    public Integer getIdApartment() {
        return idApartment;
    }

    /**
     * Getter for idRoom.
     * @return The identifier of the room.
     */
    public Integer getIdRoom() {
        return idRoom;
    }

    /**
     * Setter for idRoom.
     * @param idRoom The identifier of the room to set.
     */
    public void setIdRoom(Integer idRoom) {
        this.idRoom = idRoom;
    }

    /**
     * Setter for idApartment.
     * @param idApartment The identifier of the apartment to set.
     */
    public void setIdApartment(Integer idApartment) {
        this.idApartment = idApartment;
    }
}