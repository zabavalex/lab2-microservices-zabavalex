package com.rsoilab2.order.entities;

import com.rsoilab2.order.enumerations.OrderStatusEnumeration;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.Calendar;
import java.util.UUID;

@Entity
@Data
@Table(name = "orders", indexes = {
        @Index(name = "idx_orders_user_uid", columnList = "user_uid"),
        @Index( name = "idx_orders_order_uid", columnList = "order_uid", unique = true),
        @Index(name = "idx_orders_user_and_order_uid", columnList = "order_uid, user_uid")
})
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, length = 40)
    private UUID itemUid;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Calendar orderDate;
    @Column(name = "order_uid", nullable = false, unique = true, length = 40)
    private UUID orderUid;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatusEnumeration status;
    @Column(name = "user_uid",nullable = false, length = 40)
    private UUID userUid;
}
