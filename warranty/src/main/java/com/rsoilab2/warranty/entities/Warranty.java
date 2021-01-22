package com.rsoilab2.warranty.entities;

import com.rsoilab2.warranty.enumerations.WarrantyStatus;
import lombok.Data;

import javax.persistence.*;
import java.util.Calendar;
import java.util.UUID;

@Entity
@Table(name = "warranty", indexes = {
        @Index(name = "idx_warranty_item_uid", columnList = "itemUid", unique = true)
})
@Data
public class Warranty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 1024)
    private String comment;

    @Column(nullable = false, unique = true, length = 40)
    private UUID itemUid;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private WarrantyStatus status;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar warrantyDate;
}
