package com.rsoilab2.warehouse.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemRequest {
    private UUID orderUid;
    @NotEmpty(message = "Field model is empty")
    private String model;
    private String size;
}
