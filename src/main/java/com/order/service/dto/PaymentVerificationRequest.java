package com.order.service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Map;

@Data
public class PaymentVerificationRequest {

    /**
     * Internal Payment ID
     */
    @NotBlank(message = "Payment ID is required")
    private String paymentId;

    /**
     * Order Reference ID
     */
    private String orderId;

    /**
     * Gateway Transaction ID
     */
    @NotBlank(message = "Transaction ID is required")
    private String transactionId;

    /**
     * Payment Gateway Name
     * Example:
     * RAZORPAY
     * STRIPE
     * PHONEPE
     * PAYTM
     */
    @NotBlank(message = "Provider is required")
    private String provider;

    /**
     * Gateway payment reference
     */
    private String gatewayPaymentId;

    /**
     * Gateway order reference
     */
    private String gatewayOrderId;

    /**
     * Signature received from payment gateway
     * Used for signature validation
     */
    private String signature;

    /**
     * Payment status received from gateway
     * SUCCESS / FAILED / PENDING
     */
    private String paymentStatus;

    /**
     * Verification source
     * FRONTEND_CALLBACK
     * WEBHOOK
     * SYSTEM_RETRY
     */
    private String verificationSource;

    /**
     * Customer reference
     */
    private String customerId;

    /**
     * Generic gateway payload
     */
    private Map<String, Object> gatewayPayload;

    /**
     * Additional metadata
     */
    private Map<String, Object> metadata;
}