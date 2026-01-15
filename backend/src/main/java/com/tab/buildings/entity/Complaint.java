package com.tab.buildings.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;
/**
 * Represents a complaint registered by a tenant.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "complaint")
public class Complaint {

    /**
     * Unique identifier for the complaint.
     */
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id_complaint", nullable = false)
    @Getter @Setter
    private int id_complaint;

    /**
     * Title of the complaint.
     */
    @Column(name = "title", nullable = false)
    @Getter @Setter
    private String title;

    /**
     * Detailed content of the complaint.
     */
    @Column(name = "content", nullable = false)
    @Getter @Setter
    private String content;

    /**
     * Date when the complaint was registered.
     */
    @Column(name = "date", nullable = false)
    @Getter @Setter
    private Date date;

    /**
     * ID of the tenant who filed the complaint.
     */
    @Column(name = "id_tenant", nullable = false)
    @Getter @Setter
    private int idTenant;

    /**
     * ID of the manager assigned to handle the complaint.
     */
    @Column(name = "id_manager", nullable = false)
    @Getter @Setter
    private int idManager;
}