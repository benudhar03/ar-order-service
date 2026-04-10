package com.order.service.repository;

import com.order.service.entity.Order;
import com.order.service.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends MongoRepository<Order, String> {

    Optional<Order> findByOrderId(String orderId);

    boolean existsByOrderId(String orderId);

    boolean existsByOrderIdAndStatus(String orderId, OrderStatus status);

    Optional<Order> findByOrderIdAndStatus(String orderId, OrderStatus status);

    Page<Order> findByUserId(String userId, Pageable pageable);
}
