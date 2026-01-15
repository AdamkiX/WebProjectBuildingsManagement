package com.tab.buildings.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
/**
 * Entity class representing a building.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "building")
public class Building {

    /**
     * Unique identifier for the building.
     */
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id_building", nullable = false)
    @Getter @Setter
    private Integer id_building;

    /**
     * Address of the building.
     */
    @Column(name = "address", nullable = false, length = 100)
    @Getter @Setter
    private String address;

    /**
     * City where the building is located.
     */
    @Column(name = "city", nullable = false, length = 50)
    @Getter @Setter
    private String city;

    /**
     * Province where the building is located.
     */
    @Column(name = "prowince", nullable = false, length = 50)
    @Getter @Setter
    private String prowince;

    /**
     * List of apartments associated with the building.
     */
    @OneToMany(mappedBy = "id_building")
    private List<Apartment> apartments;

    /**
     * Transient field representing the number of apartments in the building.
     */
    @Transient
    private int apartmentNumber = 0;

}