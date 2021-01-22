package com.rsoilab2.warehouse.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "order_item", indexes = {
        @Index(name = "idx_order_item_item_id", columnList = "items_id"),
        @Index(name = "idx_order_item_order_uid", columnList = "orderUid"),
        @Index(name = "idx_order_item_item_uid", columnList = "orderItemUid", unique = true)
})
@Data
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Boolean canceled;

    @Column(nullable = false, length = 40, unique = true)
    private UUID orderItemUid;

    @Column(nullable = false, length = 40)
    private UUID OrderUid;

    @ManyToOne
    @JoinColumn(name = "items_id", foreignKey = @ForeignKey(name = "fk_order_item_item_id"))
    private Items items;
}
