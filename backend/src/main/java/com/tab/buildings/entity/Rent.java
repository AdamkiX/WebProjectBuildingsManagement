package com.tab.buildings.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
/**
 * Entity class representing a rent record.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "rent")
public class Rent {

    /**
     * Primary key ID for rent record.
     */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id_rent", nullable = false)
    @Getter
    @Setter private Integer id;

    /**
     * Date when the rent record was added.
     */
    @Column(name = "add_date")
    @Getter @Setter private Date addDate;

    /**
     * End date of the rent period.
     */
    @Column(name = "end_date")
    @Getter @Setter private Date endDate;

    /**
     * Start date of the rent period.
     */
    @Column(name = "start_date")
    @Getter @Setter private Date startDate;

    /**
     * Additional information related to the rent.
     */
    @Column(name = "information")
    @Getter @Setter private String information;

    /**
     * Amount of rent.
     */
    @Column(name = "amount")
    @Getter @Setter private Double amount;

    /**
     * Indicates if the rent has been paid.
     */
    @Column(name = "payed")
    @Getter @Setter private boolean payed;

    /**
     * Foreign key referencing the apartment associated with this rent record.
     */
    @Column(name = "id_apartment")
    @Getter @Setter private Integer idApartment;

    /**
     * Foreign key referencing the agreement associated with this rent record.
     */
    @Column(name = "id_agreement")
    @Getter @Setter private Integer idAgreement;

}