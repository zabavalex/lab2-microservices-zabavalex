package com.rsoilab2.store.models;

import com.rsoilab2.store.enumerations.SizeEnumeration;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class PurchaseRequest {
    @NotEmpty(message = "field model is empty")
    private String model;
    private SizeEnumeration size;
}
