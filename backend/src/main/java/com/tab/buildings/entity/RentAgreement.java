package com.tab.buildings.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
/**
 * Entity class representing a rent agreement.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "rent_agreement")
public class RentAgreement {

    /**
     * Unique identifier for the rent agreement.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rent_agreement", nullable = false)
    @Getter @Setter
    private Integer id;

    /**
     * Date when the agreement was made.
     */
    @Column(name = "agreement_date", nullable = false)
    @Getter @Setter
    private Date agrDate;

    /**
     * Start date of the rent period.
     */
    @Column(name = "rent_start_date", nullable = false)
    @Getter @Setter
    private Date rentStartDate;

    /**
     * End date of the rent period.
     */
    @Column(name = "rent_end_date", nullable = false)
    @Getter @Setter
    private Date rentEndDate;

    /**
     * Price of the rent.
     */
    @Column(name = "rent_price", nullable = false)
    @Getter @Setter
    private double rentPrice;

    /**
     * ID of the tenant involved in the agreement.
     */
    @Column(name = "id_tenant", nullable = false)
    @Getter @Setter
    private Integer idTenant;

    /**
     * ID of the apartment involved in the agreement.
     */
    @Column(name = "id_apartment", nullable = false)
    @Getter @Setter
    private Integer idApartment;

    /**
     * ID of the manager responsible for the agreement.
     */
    @Column(name = "id_manager", nullable = false)
    @Getter @Setter
    private Integer idManager;

    /**
     * Current status of the rent agreement.
     */
    @Column(name = "status", nullable = false)
    @Getter @Setter
    private String status;
}