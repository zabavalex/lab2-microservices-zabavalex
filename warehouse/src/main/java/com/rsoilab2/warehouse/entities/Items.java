package com.rsoilab2.warehouse.entities;

import com.rsoilab2.warehouse.enumerations.SizeEnumeration;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Items {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer availableCount;
    @Column(nullable = false)
    private String model;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SizeEnumeration size;
}

