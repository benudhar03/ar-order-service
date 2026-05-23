package com.order.service.mapper;

import com.order.service.dto.*;
import com.order.service.entity.PaymentTransaction;
import com.order.service.enums.PaymentStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class PaymentMapper {

    public PaymentTransaction toPaymentTransaction(PaymentInitiateRequest request) {

        return PaymentTransaction.builder()
                .paymentId(generatePaymentId())
                .orderId(request.getOrderId())
                .customerId(request.getCustomerId())
                .amount(request.getAmount())
                .currency(request.getCurrency())
                .paymentMethod(request.getPaymentMethod())
                .provider(request.getPaymentProvider())
                .build();
    }

    public void updatePaymentTransaction(PaymentTransaction paymentTransaction, PaymentInitiateResponse response) {

        paymentTransaction.setTransactionId(response.getTransactionId());
        paymentTransaction.setPaymentStatus(response.getPaymentStatus());
        paymentTransaction.setGatewayResponse(response.getGatewayResponse());
        paymentTransaction.setUpdatedAt(LocalDateTime.now());
    }

    public PaymentInitiateResponse toPaymentInitiateResponse(PaymentTransaction paymentTransaction, PaymentInitiateResponse response) {

        return PaymentInitiateResponse.builder()
                .paymentId(paymentTransaction.getPaymentId())
                .orderId(paymentTransaction.getOrderId())
                .transactionId(response.getTransactionId())
                .paymentStatus(response.getPaymentStatus())
                .paymentUrl(response.getPaymentUrl())
                .provider(paymentTransaction.getProvider())
                .paymentMethod(paymentTransaction.getPaymentMethod())
                .amount(paymentTransaction.getAmount())
                .currency(paymentTransaction.getCurrency())
                .gatewayResponse(response.getGatewayResponse())
                .message(response.getMessage())
                .initiatedAt(LocalDateTime.now())
                .build();
    }

    public PaymentStatusResponse toPaymentStatusResponse(PaymentTransaction paymentTransaction) {

        return PaymentStatusResponse.builder()
                .paymentId(paymentTransaction.getPaymentId())
                .orderId(paymentTransaction.getOrderId())
                .transactionId(paymentTransaction.getTransactionId())
                .paymentStatus(paymentTransaction.getPaymentStatus())
                .provider(paymentTransaction.getProvider())
                .paymentMethod(paymentTransaction.getPaymentMethod())
                .amount(paymentTransaction.getAmount())
                .currency(paymentTransaction.getCurrency())
                .gatewayResponse(paymentTransaction.getGatewayResponse())
                .createdAt(paymentTransaction.getCreatedAt())
                .updatedAt(paymentTransaction.getUpdatedAt())
                .build();
    }

    public void updatePaymentVerification(
            PaymentTransaction paymentTransaction, PaymentVerificationResponse response) {

        paymentTransaction.setPaymentStatus(response.getPaymentStatus());
        paymentTransaction.setGatewayResponse(response.getGatewayResponse());
        paymentTransaction.setUpdatedAt(LocalDateTime.now());
    }

    public void updateRefundStatus(PaymentTransaction paymentTransaction, RefundRequest request) {
        if (request.getRefundAmount().compareTo(paymentTransaction.getAmount()) == 0) {
            paymentTransaction.setPaymentStatus(PaymentStatus.REFUNDED.name());
        } else {
            paymentTransaction.setPaymentStatus(PaymentStatus.PARTIAL_REFUND.name());
        }
        paymentTransaction.setUpdatedAt(LocalDateTime.now());
    }

    public CommonApiResponse toCancelResponse() {
        return CommonApiResponse.builder()
                .success(true)
                .code("PAYMENT_CANCELLED")
                .message("Payment cancelled successfully")
                .timestamp(LocalDateTime.now())
                .build();
    }

    private String generatePaymentId() {
        return "PAY_" +
                UUID.randomUUID()
                        .toString()
                        .replace("-", "")
                        .substring(0, 12)
                        .toUpperCase();
    }
}