package com.rsoilab2.order.repositories;

import com.rsoilab2.order.entities.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
    Optional<Order> findByUserUidAndOrderUid(UUID userUid, UUID orderUid);
    List<Order> findByUserUid(UUID userUid);
    Optional<Order> findByOrderUid(UUID orderUid);
    void deleteByOrderUid(UUID orderUid);
}
