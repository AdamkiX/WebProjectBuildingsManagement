package com.tab.buildings.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

/**
 * Entity class representing a payment made for rent.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "payment")
public class Payment {

    /**
     * Unique identifier for the payment.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_payment", nullable = false)
    @Getter @Setter
    private int id_payment;

    /**
     * Date when the payment was made.
     */
    @Column(name = "payment_date", nullable = false)
    @Getter @Setter
    private Date payment_date;

    /**
     * Amount of money paid.
     */
    @Column(name = "amount", nullable = false)
    @Getter @Setter
    private Integer amount;

    /**
     * Identifier for the rent associated with this payment.
     */
    @Column(name = "id_rent", nullable = false)
    @Getter @Setter
    private Integer idRent;
}