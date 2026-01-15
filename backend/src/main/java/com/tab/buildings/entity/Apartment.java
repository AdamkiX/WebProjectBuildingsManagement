package com.tab.buildings.entity;

import jakarta.persistence.*;
import lombok.*;
/**
 * Entity class representing an Apartment.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "apartment")
public class Apartment {

    /**
     * The unique identifier for the apartment.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_apartment", nullable = false)
    @Getter @Setter
    private int id;

    /**
     * The apartment number within the building.
     */
    @Column(name = "apt_number", nullable = false)
    @Getter @Setter
    private int apt_number;

    /**
     * The level or floor of the apartment.
     */
    @Column(name = "level", nullable = false)
    @Getter @Setter
    private int level;

    /**
     * The area of the apartment in square meters.
     */
    @Column(name = "apt_area", nullable = false)
    @Getter @Setter
    private float apt_area;

    /**
     * Additional details or description of the apartment.
     */
    @Column(name = "details")
    @Getter @Setter
    private String details;

    /**
     * The ID of the tenant currently occupying the apartment (nullable).
     */
    @Column(name = "id_tenant", nullable = true)
    @Getter @Setter
    private Integer idTenant;

    /**
     * The ID of the building to which the apartment belongs.
     */
    @Column(name = "id_building", nullable = false)
    @Getter @Setter
    private Integer id_building;

    /**
     * The ID of the manager responsible for the apartment (nullable).
     */
    @Column(name = "id_manager")
    @Getter @Setter
    private Integer idManager;
}