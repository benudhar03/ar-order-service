package com.order.service.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
public class RefundResponse {

    /**
     * Refund ID
     */
    private String refundId;

    /**
     * Original Payment ID
     */
    private String paymentId;

    /**
     * Order ID
     */
    private String orderId;

    /**
     * Gateway Refund Transaction ID
     */
    private String refundTransactionId;

    /**
     * SUCCESS
     * PENDING
     * FAILED
     */
    private String refundStatus;

    /**
     * Refunded Amount
     */
    private BigDecimal refundAmount;

    /**
     * Remaining Amount after refund
     */
    private BigDecimal remainingAmount;

    /**
     * Refund Reason
     */
    private String refundReason;

    /**
     * Gateway Name
     */
    private String provider;

    /**
     * Gateway response
     */
    private Map<String, Object> gatewayResponse;

    private String message;

    private LocalDateTime refundedAt;
}