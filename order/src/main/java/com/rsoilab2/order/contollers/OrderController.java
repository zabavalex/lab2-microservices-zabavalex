package com.rsoilab2.order.contollers;

import com.rsoilab2.order.entities.Order;
import com.rsoilab2.order.grpc.WarehouseServiceGrpcClient;
import com.rsoilab2.order.grpc.WarrantyServiceGrpcClient;
import com.rsoilab2.order.models.*;
import com.rsoilab2.order.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@RestController
@RequestMapping ("/api/v1/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private WarehouseServiceGrpcClient warehouseServiceGrpcClient;
    @Autowired
    private WarrantyServiceGrpcClient warrantyServiceGrpcClient;

    @PostMapping("/{userUid}")
    public ResponseEntity<?> performOrder(@PathVariable String userUid, @RequestBody OrderRequest orderRequest){
        UUID orderUid = orderService.performOrder(orderRequest.getModel(), orderRequest.getSize().name(), userUid);
        return ResponseEntity.ok(new OrderResponse(orderUid));
    }

    @GetMapping("/{userUid}/{orderUid}")
    public ResponseEntity<?> getInformationAboutOrderByUserUidAndOrderUid(@PathVariable String orderUid,
                                                                           @PathVariable String userUid){
        Order order = orderService.findOrderByUserUidAndOrderUid(UUID.fromString(userUid), UUID.fromString(orderUid));
        return ResponseEntity.ok(new OrderInfoResponse(order.getOrderUid(), order.getOrderDate().getTime().toString(), order.getItemUid(), order.getStatus().name()));
    }

    @GetMapping("/{userUid}")
    public ResponseEntity<?> getAllOrdersByUserUid(@PathVariable String userUid){
        return ResponseEntity.ok(
                orderService.findAllOrderByUserUid(UUID.fromString(userUid)).stream()
                        .map(o -> new OrderInfoResponse(o.getOrderUid(), o.getOrderDate().getTime().toString(), o.getItemUid(), o.getStatus().name()))
                        .collect(Collectors.toCollection(OrdersInfoResponse::new))
        );
    }

    @PostMapping("/{orderUid}/warranty")
    public ResponseEntity<?> getWarrantyByOrderUid(@PathVariable String orderUid,
                                                    @RequestBody OrderWarrantyRequest request){
        Order order = orderService.findOrderByOrderUid(UUID.fromString(orderUid));
        OrderWarrantyResponse useWarrantyItemResponse = warehouseServiceGrpcClient.useWarranty(
                order.getItemUid().toString(), request
        );
        return ResponseEntity.ok(useWarrantyItemResponse);
    }

    @DeleteMapping("/{orderUid}")
    public ResponseEntity<?> returnOrder(@PathVariable String orderUid){
        Order order = orderService.findOrderByOrderUid(UUID.fromString(orderUid));

        String itemUid = order.getItemUid().toString();
        warehouseServiceGrpcClient.returnItem(itemUid);
        warrantyServiceGrpcClient.stopWarranty(itemUid);

        orderService.deleteOrder(order.getOrderUid());

        return ResponseEntity.noContent().build();
    }

}
