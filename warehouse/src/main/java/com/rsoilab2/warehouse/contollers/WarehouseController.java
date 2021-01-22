package com.rsoilab2.warehouse.contollers;

import com.rsoilab2.warehouse.entities.Items;
import com.rsoilab2.warehouse.entities.OrderItem;
import com.rsoilab2.warehouse.enumerations.SizeEnumeration;
import com.rsoilab2.warehouse.exceptions.ItemNotAvailableException;
import com.rsoilab2.warehouse.grpc.WarrantyServiceGrpcClient;
import com.rsoilab2.warehouse.models.*;
import com.rsoilab2.warehouse.services.ItemsService;
import com.rsoilab2.warehouse.services.OrderItemService;
import com.rsoilab2.warehouse.services.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/warehouse")
public class WarehouseController {
    @Autowired
    private ItemsService itemsService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private WarrantyServiceGrpcClient warrantyServiceGrpcClient;
    @Autowired
    private WarehouseService warehouseService;

    @GetMapping("/{orderItemUid}")
    public ResponseEntity<?> getInformationAboutStaffInWarehouseByOrderItemUid(@PathVariable String orderItemUid){
        Items items = orderItemService.findByOrderItemUid(UUID.fromString(orderItemUid)).getItems();
        return ResponseEntity.ok(new ItemInfoResponse(items.getModel(), items.getSize().name()));
    }

    @PostMapping
    public ResponseEntity<?> performGetNewStuffByNewOrderRequest(@RequestBody OrderItemRequest orderItemRequest){
        return ResponseEntity.ok(warehouseService.takeItem(orderItemRequest));
    }
    @PostMapping("/{orderItemUid}/warranty")
    public ResponseEntity<?> performDecisionByWarrantyRequest(@PathVariable String orderItemUid, @RequestBody WarrantyRequest orderWarrantyRequest){
        WarrantyResponse orderWarrantyResponse = warrantyServiceGrpcClient.performWarrantyRequest(orderItemUid, orderWarrantyRequest);
        return ResponseEntity.ok(orderWarrantyResponse);
    }
    @DeleteMapping("/{orderItemUid}")
    public ResponseEntity<?> returnOrderToWarehouse(@PathVariable String orderItemUid){
        OrderItem orderItem = orderItemService.findByOrderItemUid(UUID.fromString(orderItemUid));
        orderItem.getItems().setAvailableCount(orderItem.getItems().getAvailableCount() + 1);
        itemsService.save(orderItem.getItems());

        orderItem.setCanceled(true);
        orderItemService.save(orderItem);

        return ResponseEntity.noContent().build();
    }
}
