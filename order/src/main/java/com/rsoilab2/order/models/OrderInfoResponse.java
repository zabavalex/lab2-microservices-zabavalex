package com.rsoilab2.order.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderInfoResponse {
    private UUID orderUid;
    private String orderDate;
    private UUID itemUid;
    private String status;
}
