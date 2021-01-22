package com.rsoilab2.warranty.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WarrantyResponse {
    private String decision;
    private String warrantyDate;
}