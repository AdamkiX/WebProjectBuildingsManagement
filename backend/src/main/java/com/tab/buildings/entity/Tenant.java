package com.tab.buildings.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
/**
 * Entity class representing a tenant.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tenant")
public class Tenant {

    //********************* ID ******************************//
    /**
     * Unique identifier for the tenant.
     */
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id_tenant", nullable = false)
    @Getter @Setter private int id;

    //********************* NAME ******************************//
    /**
     * Name of the tenant.
     */
    @Column(name = "name", nullable = false, length = 64)
    @Getter @Setter private String name;

    //********************* SURNAME ******************************//
    /**
     * Surname of the tenant.
     */
    @Column(name = "surname", nullable = false, length = 64)
    @Getter @Setter private String surname;

    //********************* BIRTHDATE ******************************//
    /**
     * Birthdate of the tenant (format: yyyy-MM-dd).
     */
    @Column(name = "birthdate", nullable = false, length = 11)
    @Getter @Setter private String birthdate;

    //********************* PHONE ******************************//
    /**
     * Phone number of the tenant.
     */
    @Column(name = "phone", nullable = false, length = 9)
    @Getter @Setter private int phone;

    //********************* RESIDENCE ******************************//
    /**
     * Residence address of the tenant.
     */
    @Column(name = "residence", nullable = false, length = 400)
    @Getter @Setter private String residence;

}