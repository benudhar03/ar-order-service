package com.order.service.service;

import com.order.service.dto.*;
import com.order.service.strategy.PaymentGatewayStrategy;
import com.order.service.mapper.PaymentMapper;
import com.order.service.repository.PaymentTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentTransactionRepository paymentTransactionRepository;
    private final PaymentGatewayStrategy paymentStrategyFactory;
    private final PaymentMapper paymentMapper;


    @Override
    public PaymentInitiateResponse initiatePayment(PaymentInitiateRequest request) {
        return null;
    }

    @Override
    public PaymentStatusResponse getPaymentStatus(String paymentId) {
        return null;
    }

    @Override
    public PaymentVerificationResponse verifyPayment(PaymentVerificationRequest request) {
        return null;
    }

    @Override
    public RefundResponse refundPayment(RefundRequest request) {
        return null;
    }

    @Override
    public CommonApiResponse cancelPayment(String paymentId) {
        return null;
    }

    @Override
    public void handlePaymentCallback(String provider, String payload, String signature) {

    }
}
