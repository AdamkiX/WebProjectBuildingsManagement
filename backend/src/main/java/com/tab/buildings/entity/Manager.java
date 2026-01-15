package com.tab.buildings.entity;

import jakarta.persistence.*;
import lombok.*;


/**
 * Represents a manager entity in the system.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "manager")
public class Manager {

    /** The unique identifier for the manager. */
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id_manager", nullable = false)
    @Getter @Setter
    private int id;

    /** The name of the manager. */
    @Column(name = "name", nullable = false, length = 64)
    @Getter @Setter
    private String name;

    /** The surname of the manager. */
    @Column(name = "surname", nullable = false, length = 64)
    @Getter @Setter
    private String surname;

    /** The birthdate of the manager (format: yyyy-MM-dd). */
    @Column(name = "birthdate", nullable = false)
    @Getter @Setter
    private String birthdate;

    /** The phone number of the manager. */
    @Column(name = "phone", nullable = false, length = 9)
    @Getter @Setter
    private int phone;

    /** The residence address of the manager. */
    @Column(name = "residence", nullable = false, length = 400)
    @Getter @Setter
    private String residence;

}
