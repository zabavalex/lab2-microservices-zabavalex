package com.rsoilab2.order.services;

import com.rsoilab2.order.entities.Order;
import com.rsoilab2.order.enumerations.OrderStatusEnumeration;
import com.rsoilab2.order.grpc.WarehouseServiceGrpcClient;
import com.rsoilab2.order.grpc.WarrantyServiceGrpcClient;
import com.rsoilab2.order.models.OrderItemResponse;
import com.rsoilab2.order.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private WarehouseServiceGrpcClient warehouseServiceGrpcClient;
    @Autowired
    private WarrantyServiceGrpcClient warrantyServiceGrpcClient;

    @Transactional
    public void save(Order order){
        orderRepository.save(order);
    }

    @Transactional
    public Order findOrderByUserUidAndOrderUid(UUID userUid, UUID orderUid){
        return orderRepository.findByUserUidAndOrderUid(userUid, orderUid)
                .orElseThrow(() -> new EntityNotFoundException("Not found order " + orderUid.toString() + " for user " + userUid.toString()));
    }

    @Transactional
    public List<Order> findAllOrderByUserUid(UUID userUid){
        return orderRepository.findByUserUid(userUid);
    }

    @Transactional
    public Order findOrderByOrderUid(UUID orderUid){
        return orderRepository.findByOrderUid(orderUid).
                orElseThrow(() -> new EntityNotFoundException("Not found order " + orderUid.toString()));
    }

    @Transactional
    public void deleteOrder(UUID orderUid){
        orderRepository.deleteByOrderUid(orderUid);
    }

    @Transactional
    public UUID performOrder(String model, String size, String userUid){
        UUID orderUid = UUID.randomUUID();
        OrderItemResponse takeItemResponse = warehouseServiceGrpcClient.takeItem(orderUid.toString(),
                model, size);
        String orderItemUid = takeItemResponse.getOrderItemUid().toString();
        warrantyServiceGrpcClient.startWarranty(orderItemUid);
        Order order = new Order();
        order.setItemUid(UUID.fromString(orderItemUid));
        order.setOrderDate(Calendar.getInstance());
        order.setOrderUid(orderUid);
        order.setUserUid(UUID.fromString(userUid));
        order.setStatus(OrderStatusEnumeration.PAID);
        this.save(order);
        return orderUid;
    }

}
