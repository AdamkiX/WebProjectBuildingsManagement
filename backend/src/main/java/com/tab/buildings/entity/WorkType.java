package com.tab.buildings.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Represents a work type entity.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "worktype")
public class WorkType {

    /**
     * The unique identifier for the work type.
     */
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id_work_type", nullable = false)
    @Getter @Setter private int id_work_type;

    /**
     * The name of the work type.
     */
    @Column(name = "work_type", nullable = false)
    @Getter @Setter private String work_type;
}