package com.rsoilab2.store.contollers;


import com.rsoilab2.store.grpc.*;
import com.rsoilab2.store.models.*;
import com.rsoilab2.store.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/store")
public class StoreController {
    @Autowired
    private UserService userService;
    @Autowired
    private OrderServiceGrpcClient orderServiceGrpcClient;
    @Autowired
    private WarehouseServiceGrpcClient warehouseServiceGrpcClient;
    @Autowired
    private WarrantyServiceGrpcClient warrantyServiceGrpcClient;

    @GetMapping("/{userUid}/orders")
    public ResponseEntity<?> getAllOrdersByUserUid(@PathVariable String userUid){
        userService.findUserByUserUid(UUID.fromString(userUid));
        List<UserOrderResponse> userOrderResponseList = orderServiceGrpcClient.getOrdersByUserUid(userUid).stream()
                .map(o -> {
                    ItemInfoResponse itemResponse = warehouseServiceGrpcClient.getItem(String.valueOf(o.getItemUid()));
                    WarrantyInformationResponse itemWarrantyInfo = warrantyServiceGrpcClient
                            .getItemWarrantyInfo(String.valueOf(o.getItemUid()));
                    return new UserOrderResponse(o, itemResponse, itemWarrantyInfo);
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(userOrderResponseList);
    }

    @GetMapping("/{userUid}/{orderUid}")
    public ResponseEntity<?> getOrderByOrderUidAndUserUid(@PathVariable String userUid, @PathVariable String orderUid){
        userService.findUserByUserUid(UUID.fromString(userUid));
        OrderInfoResponse userOrderResponseAnswer = orderServiceGrpcClient.getOrdersByUserUid(userUid).stream()
                .filter(o -> o.getOrderUid().toString().equals(orderUid)).findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Order " + orderUid + " not found for user " + userUid));
        ItemInfoResponse itemResponse = warehouseServiceGrpcClient.getItem(String.valueOf(userOrderResponseAnswer.getItemUid()));
        WarrantyInformationResponse itemWarrantyInfo = warrantyServiceGrpcClient
                .getItemWarrantyInfo(String.valueOf(userOrderResponseAnswer.getItemUid()));
        return ResponseEntity.ok(new UserOrderResponse(userOrderResponseAnswer, itemResponse, itemWarrantyInfo));
    }

    @PostMapping("/{userUid}/{orderUid}/warranty")
    public ResponseEntity<?> getWarrantyByUserUidAndOrderUid(@PathVariable String userUid,
                                                             @PathVariable String orderUid,
                                                             @RequestBody WarrantyRequest warrantyRequest){
        userService.findUserByUserUid(UUID.fromString(userUid));
        OrderWarrantyResponse warrantyResponse = orderServiceGrpcClient
                .getWarrantyByOrderUid(orderUid, new OrderWarrantyRequest(warrantyRequest.getReason()));
        return ResponseEntity.ok(new WarrantyResponse(orderUid,
                warrantyResponse.getWarrantyDate(), warrantyResponse.getDecision()));
    }

    @PostMapping("/{userUid}/purchase")
    public ResponseEntity<?> performPurchase(@PathVariable String userUid, @RequestBody PurchaseRequest request){
        userService.findUserByUserUid(UUID.fromString(userUid));
        OrderResponse response = orderServiceGrpcClient.performOrder(request, userUid);
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{orderUid}")
                .buildAndExpand(response.getOrderUid()).toUri()).build();
    }

    @DeleteMapping("/{userUid}/{orderUid}/refund")
    public ResponseEntity<?> performRefund(@PathVariable String userUid, @PathVariable String orderUid){
        userService.findUserByUserUid(UUID.fromString(userUid));
        orderServiceGrpcClient.refundOrder(orderUid);
        return ResponseEntity.noContent().build();
    }
}
