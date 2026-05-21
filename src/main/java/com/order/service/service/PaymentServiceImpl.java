package com.order.service.service;

import com.order.service.dto.*;
import com.order.service.entity.PaymentTransaction;
import com.order.service.enums.PaymentProvider;
import com.order.service.enums.PaymentStatus;
import com.order.service.exception.PaymentException;
import com.order.service.strategy.PaymentGatewayStrategy;
import com.order.service.mapper.PaymentMapper;
import com.order.service.repository.PaymentTransactionRepository;
import com.order.service.strategy.PaymentStrategyFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentTransactionRepository paymentTransactionRepository;
    private final PaymentStrategyFactory paymentStrategyFactory;
    private final PaymentMapper paymentMapper;

    /**
     * TODO
     * */
    @Override
    public PaymentInitiateResponse initiatePayment(PaymentInitiateRequest request) {
        log.info("Initiating payment for orderId: {}", request.getOrderId());

        validatePaymentRequest(request);

        PaymentTransaction paymentTransaction = paymentMapper.toPaymentTransaction(request);

        paymentTransaction.setPaymentStatus(PaymentStatus.INITIATED.name());
        paymentTransaction.setCreatedAt(LocalDateTime.now());
        paymentTransaction.setUpdatedAt(LocalDateTime.now());

        paymentTransactionRepository.save(paymentTransaction);
        PaymentGatewayStrategy strategy =
                paymentStrategyFactory.getStrategy(request.getPaymentProvider());
        PaymentInitiateResponse gatewayResponse = strategy.initiatePayment(request);

        paymentMapper.updatePaymentTransaction(paymentTransaction, gatewayResponse);

        paymentTransactionRepository.save(paymentTransaction);

        log.info("Payment initiated successfully paymentId: {}", paymentTransaction.getPaymentId());

        return paymentMapper.toPaymentInitiateResponse(paymentTransaction, gatewayResponse);
    }

    @Override
    public PaymentStatusResponse getPaymentStatus(String paymentId) {

        log.info("Fetching payment status paymentId: {}", paymentId);
        PaymentTransaction paymentTransaction = getPaymentById(paymentId);
        return paymentMapper.toPaymentStatusResponse(paymentTransaction);
    }

    @Override
    public PaymentVerificationResponse verifyPayment(PaymentVerificationRequest request) {
        log.info("Verifying payment paymentId: {}", request.getPaymentId());
        PaymentTransaction paymentTransaction = getPaymentById(request.getPaymentId());

        PaymentGatewayStrategy strategy =
                paymentStrategyFactory.getStrategy(request.getProvider());

        PaymentVerificationResponse response = strategy.verifyPayment(request);

        paymentMapper.updatePaymentVerification(
                paymentTransaction,
                response);

        paymentTransactionRepository.save(paymentTransaction);

        log.info("Payment verified successfully paymentId: {}", request.getPaymentId());
        return response;
    }

    @Override
    public RefundResponse refundPayment(RefundRequest request) {
        log.info("Refund initiated paymentId: {}", request.getPaymentId());

        PaymentTransaction paymentTransaction = getPaymentById(request.getPaymentId());
        validateRefund(paymentTransaction);

        PaymentGatewayStrategy strategy =
                paymentStrategyFactory.getStrategy(paymentTransaction.getProvider());

        RefundResponse refundResponse = strategy.refundPayment(request);

        paymentMapper.updateRefundStatus(paymentTransaction, request);
        paymentTransactionRepository.save(paymentTransaction);

        log.info("Refund completed paymentId: {}", request.getPaymentId());
        return refundResponse;
    }

    @Override
    public CommonApiResponse cancelPayment(String paymentId) {
        log.info("Cancelling payment paymentId: {}",
                paymentId);

        PaymentTransaction paymentTransaction = getPaymentById(paymentId);
        paymentTransaction.setPaymentStatus(PaymentStatus.CANCELLED.name());

        paymentTransaction.setUpdatedAt(LocalDateTime.now());

        paymentTransactionRepository.save(paymentTransaction);

        log.info("Payment cancelled paymentId: {}", paymentId);

        return CommonApiResponse.builder()
                .success(true)
                .code("PAYMENT_CANCELLED")
                .message("Payment cancelled successfully")
                .timestamp(LocalDateTime.now())
                .build();
    }

    @Override
    public void handlePaymentCallback(String provider, String payload, String signature) {
        log.info("Handling payment callback provider: {}",
                provider);

        PaymentGatewayStrategy strategy =
                paymentStrategyFactory.getStrategy(PaymentProvider.valueOf(provider));

        strategy.handleCallback(payload, signature);
        log.info("Payment callback processed successfully");
    }

    private void validateRefund(PaymentTransaction paymentTransaction) {
        if (!PaymentStatus.SUCCESS.equals(paymentTransaction.getPaymentStatus())) {
            throw new PaymentException("Only successful payments can be refunded");
        }
    }

    private PaymentTransaction getPaymentById(String paymentId) {
        return paymentTransactionRepository.findByPaymentId(paymentId)
                .orElseThrow(() ->
                        new PaymentException("Payment not found"));
    }

    private void validatePaymentRequest(PaymentInitiateRequest request) {

        if (request.getAmount() == null || request.getAmount().doubleValue() <= 0) {
            throw new PaymentException("Invalid payment amount");
        }

        if (request.getPaymentProvider() == null) {
            throw new PaymentException("Payment provider is required");
        }

        if (request.getPaymentMethod() == null) {
            throw new PaymentException("Payment method is required");
        }
    }
}
