package com.order.service.service;

import com.order.service.dto.*;

public interface PaymentService {

    PaymentInitiateResponse initiatePayment(PaymentInitiateRequest request);

    PaymentStatusResponse getPaymentStatus(String paymentId);

    PaymentVerificationResponse verifyPayment(PaymentVerificationRequest request);

    RefundResponse refundPayment(RefundRequest request);

    CommonApiResponse cancelPayment(String paymentId);

    void handlePaymentCallback(String provider, String payload, String signature);

}
