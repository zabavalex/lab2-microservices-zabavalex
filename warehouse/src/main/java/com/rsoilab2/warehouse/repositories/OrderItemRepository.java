package com.rsoilab2.warehouse.repositories;

import com.rsoilab2.warehouse.entities.OrderItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderItemRepository extends CrudRepository<OrderItem, Long> {
    Optional<OrderItem> findByOrderItemUid(UUID orderItemUid);
}
