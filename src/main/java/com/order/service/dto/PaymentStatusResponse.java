package com.order.service.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
public class PaymentStatusResponse {

    private String paymentId;

    private String orderId;

    private String transactionId;

    /**
     * INITIATED
     * PENDING
     * SUCCESS
     * FAILED
     * CANCELLED
     * REFUNDED
     */
    private String paymentStatus;

    private String provider;

    private String paymentMethod;

    private BigDecimal amount;

    private String currency;

    private String failureReason;

    /**
     * Gateway response data
     */
    private Map<String, Object> gatewayResponse;
    private LocalDateTime completedAt;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}