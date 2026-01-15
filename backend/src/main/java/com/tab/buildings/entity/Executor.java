package com.tab.buildings.entity;

import jakarta.persistence.*;
import java.util.Date;
import lombok.*;

/**
 * Represents an executor entity in the system.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "executor")
public class Executor {

    /**
     * Unique identifier for the executor.
     */
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id_executor", nullable = false)
    private int id_executor;

    /**
     * The name of the executor.
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * The surname of the executor.
     */
    @Column(name = "surname", nullable = false)
    private String surname;

    /**
     * The phone number of the executor.
     */
    @Column(name = "phone", nullable = false)
    private String phone;

    /**
     * The birthdate of the executor.
     */
    @Column(name = "birthdate", nullable = false)
    private Date birthdate;

    /**
     * The ID of the work type associated with the executor.
     */
    @Column(name = "id_work_type", nullable = false)
    private int id_work_type;

    /**
     * The company name of the executor.
     */
    @Column(name = "company_name", nullable = false)
    private String company_name;

    /**
     * The address of the executor.
     */
    @Column(name = "address", nullable = false)
    private String address;

    /**
     * The NIP (Tax Identification Number) of the executor.
     */
    @Column(name = "nip", nullable = false)
    private String nip;

    /**
     * The REGON (National Business Registry Number) of the executor.
     */
    @Column(name = "regon", nullable = false)
    private String regon;
}