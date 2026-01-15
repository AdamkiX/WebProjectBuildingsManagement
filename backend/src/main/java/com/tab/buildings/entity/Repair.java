package com.tab.buildings.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;
/**
 * Entity class representing a repair record.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "repair")
public class Repair {

    //********************* ID ******************************//
    /**
     * Unique identifier for the repair record.
     */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id_repair", nullable = false)
    @Getter @Setter private Integer id;

    //********************* COST ******************************//
    /**
     * Cost associated with the repair.
     */
    @Column(name = "cost", nullable = false)
    @Getter @Setter private float cost;

    //********************* START_DATE ******************************//
    /**
     * Start date of the repair.
     */
    @Column(name = "start_date", nullable = false)
    @Getter @Setter private String startDate;

    //********************* END_DATE ******************************//
    /**
     * End date of the repair.
     */
    @Column(name = "end_date", nullable = false)
    @Getter @Setter private String endDate;

    //********************* PLANNED_END_DATE ******************************//
    /**
     * Planned end date of the repair.
     */
    @Column(name = "planned_end_date", nullable = false)
    @Getter @Setter private String plannedEndDate;

    //********************* PLANNED_START_DATE ******************************//
    /**
     * Planned start date of the repair.
     */
    @Column(name = "planned_start_date", nullable = false)
    @Getter @Setter private String plannedStartDate;

    //********************* ID_REPAIR_COM ******************************//
    /**
     * Identifier for the repair communication associated with this repair.
     */
    @Column(name = "id_repair_com", nullable = false)
    @Getter @Setter private Integer idRepairCom;

    //********************* ID_EXECUTOR ******************************//
    /**
     * Identifier for the executor responsible for the repair.
     */
    @Column(name = "id_executor", nullable = true)
    @Getter @Setter private Integer idExecutor;

    //********************* ID_MANAGER ******************************//
    /**
     * Identifier for the manager overseeing the repair.
     */
    @Column(name = "id_manager", nullable = false)
    @Getter @Setter private Integer idManager;

    //********************* STATUS ******************************//
    /**
     * Status of the repair, e.g., pending, in-progress, completed.
     */
    @Column(name = "status", nullable = false, columnDefinition = "varchar(255) default 'pending'")
    @Getter @Setter private String status;
}