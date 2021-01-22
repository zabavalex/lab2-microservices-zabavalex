package com.rsoilab2.store.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WarrantyInformationResponse {
    private UUID itemUid;
    private String status;
    private String warrantyDate;

}
