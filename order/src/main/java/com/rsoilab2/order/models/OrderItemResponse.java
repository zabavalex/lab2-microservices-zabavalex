package com.rsoilab2.order.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemResponse {
    private UUID orderItemUid;
    private UUID orderUid;
    private String model;
    private String size;
}
