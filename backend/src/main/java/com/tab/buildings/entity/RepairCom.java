package com.tab.buildings.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

/**
 * Entity class representing a repair communication.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "repair_com")
public class RepairCom {

    //********************* ID ******************************//
    /**
     * Unique identifier for the repair communication.
     */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id_repair_com", nullable = false)
    @Getter @Setter private Integer id;

    //********************* TITLE ******************************//
    /**
     * Title or name associated with the repair communication.
     */
    @Column(name = "title", nullable = false, length = 64)
    @Getter @Setter private String title;

    //********************* DESCRIPTION ******************************//
    /**
     * Description or details of the repair communication.
     */
    @Column(name = "description", nullable = false, length = 1000)
    @Getter @Setter private String description;

    //********************* CREATION_DATE ******************************//
    /**
     * Date when the repair communication was created.
     */
    @Column(name = "creation_date", nullable = false)
    @Getter @Setter private String creationDate;

    //********************* STATUS ******************************//
    /**
     * Status of the repair communication, e.g., pending, resolved.
     */
    @Column(name = "status", nullable = false)
    @Getter @Setter private String status;

    //********************* ID_APARTMENT ******************************//
    /**
     * Identifier of the apartment associated with the repair communication.
     */
    @Column(name = "id_apartment", nullable = false)
    @Getter @Setter private Integer idApartment;

    //********************* ID_RENT_AGREEMENT ******************************//
    /**
     * Identifier of the rent agreement associated with the repair communication.
     */
    @Column(name = "id_rent_agreement")
    @Getter @Setter private Integer idRentAgreement;
}