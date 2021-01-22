package com.rsoilab2.warranty.models;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class WarrantyRequest {
    @NotEmpty(message = "Reason cannot be null")
    private String reason;
    private Integer availableCount;

}
