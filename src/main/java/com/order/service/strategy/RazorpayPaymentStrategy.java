package com.order.service.strategy;

import com.order.service.dto.*;
import com.order.service.enums.PaymentProvider;
import com.order.service.enums.PaymentStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Component
public class RazorpayPaymentStrategy implements PaymentGatewayStrategy {

    @Override
    public PaymentProvider getProvider() {
        return PaymentProvider.RAZORPAY;
    }

    @Override
    public PaymentInitiateResponse initiatePayment(PaymentInitiateRequest request) {

        log.info("Initiating Razorpay payment");

        return PaymentInitiateResponse.builder()
                .transactionId(UUID.randomUUID().toString())
                .paymentStatus(PaymentStatus.PENDING.name())
                .paymentUrl("https://razorpay.com/payment-link")
                .gatewayResponse(
                        Map.of(
                                "gatewayOrderId",
                                "razor_order_123"))
                .message("Razorpay payment initiated")
                .initiatedAt(LocalDateTime.now())
                .build();
    }

    @Override
    public PaymentVerificationResponse verifyPayment(PaymentVerificationRequest request) {

        log.info("Verifying Razorpay payment");
        return PaymentVerificationResponse.builder()
                .paymentId(request.getPaymentId())
                .transactionId(request.getTransactionId())
                .paymentStatus(PaymentStatus.SUCCESS.name())
                .verificationStatus("VERIFIED")
                .signatureValid(true)
                .verifiedAt(LocalDateTime.now())
                .build();
    }

    @Override
    public RefundResponse refundPayment(RefundRequest request) {

        log.info("Refunding Razorpay payment");
        return RefundResponse.builder()
                .paymentId(request.getPaymentId())
                .refundStatus("SUCCESS")
                .refundAmount(request.getRefundAmount())
                .message("Refund processed")
                .refundedAt(LocalDateTime.now())
                .build();
    }

    @Override
    public void handleCallback(String payload, String signature) {
        log.info("Handling Razorpay callback");
        /*
         * Webhook validation logic
         */
    }
}