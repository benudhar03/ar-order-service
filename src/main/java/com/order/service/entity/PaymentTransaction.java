package com.order.service.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "payment_transactions")
public class PaymentTransaction {


    @Id
    private String id;

    private String paymentId;

    private String orderId;

    private String customerId;

    private String transactionId;

    private BigDecimal amount;

    private String currency;

    private String paymentMethod;

    /**
     * RAZORPAY / STRIPE / PHONEPE
     */
    private String provider;

    /**
     * INITIATED
     * SUCCESS
     * FAILED
     * REFUNDED
     */
    private String paymentStatus;

    private String failureReason;

    /**
     * Raw gateway response
     */
    private Map<String, Object> gatewayResponse;

    /**
     * Payment completion timestamp
     */
    private LocalDateTime completedAt;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}