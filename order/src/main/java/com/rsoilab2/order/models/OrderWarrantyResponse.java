package com.rsoilab2.order.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderWarrantyResponse {
    private String decision;
    private String warrantyDate;
}
