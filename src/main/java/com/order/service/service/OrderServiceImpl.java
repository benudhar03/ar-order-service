package com.order.service.service;

import com.order.service.dto.*;
import com.order.service.entity.Order;
import com.order.service.entity.OutboxEvent;
import com.order.service.enums.EventStatus;
import com.order.service.enums.OrderStatus;
import com.order.service.enums.OutboxStatus;
import com.order.service.mapper.OrderMapper;
import com.order.service.repository.OrderRepository;
import com.order.service.repository.OutboxRepository;
import com.order.service.util.IdempotencyUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static com.order.service.util.Constants.INVALID_AMOUNT;
import static com.order.service.util.Constants.DUPLICATE_ORDER;
import static com.order.service.util.Constants.CREATED;
import static com.order.service.util.Constants.ORDER_CREATED_SUCCESSFULLY;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {


    private final OrderRepository orderRepository;
    private final OutboxRepository outboxRepository;
    private final OrderMapper orderMapper;

    @Override
    @Transactional
    public ResponseObject createOrder(OrderRequest request) {

        String orderId = IdempotencyUtil.generateOrderId();
        Instant now = Instant.now();

        if (orderRepository.existsByOrderId(orderId)) {
            throw new RuntimeException(DUPLICATE_ORDER);
        }

        Order order = Order.builder()
                .orderId(orderId)
                .userId(request.getUserId())
                .amount(request.getAmount())
                .status(OrderStatus.PENDING)
                .createdAt(now)
                .updatedAt(now)
                .build();

        orderRepository.save(order);

        OrderEvent event = new OrderEvent(
                UUID.randomUUID().toString(),
                orderId,
                request.getUserId(),
                request.getAmount(),
                EventStatus.ORDER_CREATED
        );

        OutboxEvent outbox = OutboxEvent.builder()
                .aggregateId(orderId)
                .eventType(EventStatus.ORDER_CREATED.name())
                .payload(orderMapper.toJson(event))
                .status(OutboxStatus.NEW)
                .retryCount(0)
                .createdAt(now)
                .build();

        outboxRepository.save(outbox);

        return new ResponseObject(ORDER_CREATED_SUCCESSFULLY, orderId, CREATED);
    }

    @Override
    public PagedResponse<OrderResponse> getOrdersByUser(String userId, Pageable pageable) {
        Page<Order> ordersPage = orderRepository.findByUserId(userId, pageable);

        List<OrderResponse> content = ordersPage.getContent()
                .stream()
                .map(orderMapper::toResponse)
                .toList();

        return new PagedResponse<>(
                content,
                ordersPage.getNumber(),
                ordersPage.getSize(),
                ordersPage.getTotalElements(),
                ordersPage.getTotalPages()
        );
    }
}
