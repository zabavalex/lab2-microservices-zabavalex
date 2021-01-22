package com.rsoilab2.warehouse.models;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class OrderWarrantyRequest {
    @NotEmpty(message = "field reason is empty")
    private String reason;
    private int availableCount;
}
