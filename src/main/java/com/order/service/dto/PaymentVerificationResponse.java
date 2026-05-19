package com.order.service.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
public class PaymentVerificationResponse {

    private String paymentId;

    private String orderId;

    private String transactionId;

    /**
     * VERIFIED
     * FAILED
     */
    private String verificationStatus;

    /**
     * Payment Status after verification
     */
    private String paymentStatus;

    /**
     * Verification message
     */
    private String message;

    /**
     * Gateway verification response
     */
    private Map<String, Object> gatewayResponse;

    private boolean signatureValid;

    private LocalDateTime verifiedAt;
}