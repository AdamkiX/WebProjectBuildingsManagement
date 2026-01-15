package com.tab.buildings.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

/**
 * Entity class representing a room within an apartment.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "room")
public class Room {

    //********************* ID ******************************//
    /**
     * Composite identifier for the room, consisting of roomId and apartmentId.
     */
    @EmbeddedId
    @Getter @Setter private RoomId id;

    //********************* ROOM_COUNT ******************************//
    /**
     * Number of rooms in the apartment.
     */
    @Column(name = "room_count", nullable = false)
    @Getter @Setter private Integer roomCount;

}