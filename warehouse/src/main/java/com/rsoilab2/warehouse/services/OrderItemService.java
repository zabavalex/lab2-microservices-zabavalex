package com.rsoilab2.warehouse.services;

import com.rsoilab2.warehouse.entities.OrderItem;
import com.rsoilab2.warehouse.repositories.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.UUID;

@Service
public class OrderItemService {
    @Autowired
    private OrderItemRepository orderItemRepository;

    @Transactional
    public OrderItem findByOrderItemUid(UUID orderItemUid){
        return orderItemRepository.findByOrderItemUid(orderItemUid).
                orElseThrow(() -> new EntityNotFoundException("OrderItem not found for orderItemUid: " +
                        orderItemUid.toString()));
    }

    @Transactional
    public void save(OrderItem orderItem){
        orderItemRepository.save(orderItem);
    }
}
