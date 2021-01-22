package com.rsoilab2.store.models;

import com.rsoilab2.store.enumerations.SizeEnumeration;
import com.rsoilab2.store.enumerations.WarrantyStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class UserOrderResponse {
    private UUID orderUid;
    private String date;
    private String model;
    private SizeEnumeration size;
    private String warrantyDate;
    private WarrantyStatus warrantyStatus;

    public UserOrderResponse(OrderInfoResponse orderInfo, ItemInfoResponse itemResponse,
                             WarrantyInformationResponse itemWarrantyInfo) {
        this.setOrderUid(orderInfo.getOrderUid());
        this.setDate(orderInfo.getOrderDate());
        this.setModel(itemResponse.getModel());
        this.setSize(SizeEnumeration.valueOf(itemResponse.getSize()));
        this.setWarrantyDate(itemWarrantyInfo.getWarrantyDate());
        this.setWarrantyStatus(WarrantyStatus.valueOf(itemWarrantyInfo.getStatus()));
    }
}
