package com.rsoilab2.warehouse.services;

import com.rsoilab2.warehouse.entities.Items;
import com.rsoilab2.warehouse.entities.OrderItem;
import com.rsoilab2.warehouse.enumerations.SizeEnumeration;
import com.rsoilab2.warehouse.exceptions.ItemNotAvailableException;
import com.rsoilab2.warehouse.models.OrderItemRequest;
import com.rsoilab2.warehouse.models.OrderItemResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class WarehouseService {
    @Autowired
    private ItemsService itemsService;
    @Autowired
    private OrderItemService orderItemService;

    public OrderItemResponse takeItem(OrderItemRequest orderItemRequest){
        Items items = itemsService.findByModelAndSize(orderItemRequest.getModel(), SizeEnumeration.valueOf(orderItemRequest.getSize()));

        if(items.getAvailableCount() < 1){
            throw new ItemNotAvailableException("Item " + items.getId() + " is finished on warehouse");
        }
        OrderItem orderItem = new OrderItem();
        orderItem.setOrderItemUid(UUID.randomUUID());
        orderItem.setItems(items);
        orderItem.setOrderUid(orderItemRequest.getOrderUid());
        orderItem.setCanceled(false);

        orderItemService.save(orderItem);
        items.setAvailableCount(items.getAvailableCount() - 1);
        itemsService.save(items);
        return new OrderItemResponse(
                orderItem.getOrderItemUid(),
                orderItem.getOrderUid(),
                items.getModel(),
                items.getSize().name()
        );
    }
}
