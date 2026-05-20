package com.order.service.strategy;


import com.order.service.dto.*;
import com.order.service.enums.PaymentProvider;

public interface PaymentGatewayStrategy {

    PaymentProvider getProvider();

    PaymentInitiateResponse initiatePayment(PaymentInitiateRequest request);

    PaymentVerificationResponse verifyPayment(PaymentVerificationRequest request);

    RefundResponse refundPayment(RefundRequest request);

    void handleCallback(String payload, String signature);

}