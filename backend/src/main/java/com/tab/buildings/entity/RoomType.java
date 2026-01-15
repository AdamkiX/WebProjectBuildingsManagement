package com.tab.buildings.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

/**
 * Entity class representing a room type.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "room_type")
public class RoomType {

    //********************* ID ******************************//
    /**
     * Unique identifier for the room type.
     */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id_room", nullable = false)
    @Getter @Setter private Integer id;

    //********************* ROOM_NAME ******************************//
    /**
     * Name of the room type.
     */
    @Column(name = "room", nullable = false)
    @Getter @Setter private String roomName;

}