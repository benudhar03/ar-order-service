package com.order.service.strategy;


import com.order.service.dto.*;

public interface PaymentGatewayStrategy {

    PaymentInitiateResponse initiatePayment(PaymentInitiateRequest request);

    PaymentVerificationResponse verifyPayment(PaymentVerificationRequest request);

    RefundResponse refundPayment(RefundRequest request);

    void handleCallback(String payload, String signature);

}