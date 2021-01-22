package com.rsoilab2.store.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WarrantyResponse {
    String orderUid;
    String warrantyDate;
    String decision;
}
