package com.rsoilab2.store.models;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class WarrantyRequest {
    @NotEmpty(message = "field reason is empty")
    private String reason;
}
