package com.rsoilab2.warranty.models;

import com.rsoilab2.warranty.entities.Warranty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;
import java.util.UUID;

@Data
@NoArgsConstructor
public class WarrantyInformationResponse {
    private UUID itemUid;
    private String status;
    private String warrantyDate;

    public WarrantyInformationResponse(Warranty warranty) {
        this.itemUid = warranty.getItemUid();
        this.status = warranty.getStatus().name();
        this.warrantyDate = warranty.getWarrantyDate().getTime().toString();
    }
}
