package com.rsoilab2.order.models;

import com.rsoilab2.order.enumerations.SizeEnumeration;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class OrderRequest {
    @NotEmpty(message = "Field model is empty")
    private String model;
    private SizeEnumeration size;

}
