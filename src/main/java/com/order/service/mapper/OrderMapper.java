package com.order.service.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.order.service.dto.OrderResponse;
import com.order.service.entity.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderMapper {

    private final ObjectMapper objectMapper;

    public OrderResponse toResponse(Order order) {
        return OrderResponse.builder()
                .orderId(order.getOrderId())
                .userId(order.getUserId())
                .amount(order.getAmount())
                .status(order.getStatus().name())
                .build();
    }

    public String toJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException("Serialization error", e);
        }
    }
}