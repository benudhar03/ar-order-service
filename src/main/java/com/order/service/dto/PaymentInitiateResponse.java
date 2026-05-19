package com.order.service.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
public class PaymentInitiateResponse {


    private String paymentId;
    private String orderId;
    private String transactionId;

    /**
     * INITIATED
     * PENDING
     * SUCCESS
     * FAILED
     */
    private String paymentStatus;
    private String paymentUrl;

    /**
     * Payment Gateway Provider
     */
    private String provider;

    /**
     * UPI / CARD / WALLET
     */
    private String paymentMethod;
    private BigDecimal amount;
    private String currency;

    /**
     * Gateway specific data
     */
    private Map<String, Object> gatewayResponse;
    private String message;

    private LocalDateTime initiatedAt;
}