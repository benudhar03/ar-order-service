package com.order.service.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
public class RefundRequest {


    @NotBlank(message = "Payment ID is required")
    private String paymentId;

    /**
     * Order Reference
     */
    private String orderId;

    /**
     * Full refund or partial refund amount
     */
    @NotNull(message = "Refund amount is required")
    @DecimalMin(value = "1.0", message = "Refund amount must be greater than 0")
    private BigDecimal refundAmount;

    /**
     * CUSTOMER_CANCELLED
     * DUPLICATE_PAYMENT
     * PRODUCT_RETURN
     * FRAUD_TRANSACTION
     * ORDER_FAILED
     * OTHER
     */
    @NotBlank(message = "Refund reason is required")
    private String refundReason;

    /**
     * Optional detailed comments
     */
    private String remarks;

    /**
     * Instant / Normal refund
     */
    private String refundType;

    /**
     * Initiated by:
     * CUSTOMER
     * ADMIN
     * SYSTEM
     */
    private String initiatedBy;

    /**
     * Gateway provider
     * RAZORPAY / STRIPE / PHONEPE
     */
    private String provider;

    /**
     * Generic metadata
     */
    private Map<String, Object> metadata;
}