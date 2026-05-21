package com.order.service.repository;

import com.order.service.entity.PaymentTransaction;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PaymentTransactionRepository
        extends MongoRepository<PaymentTransaction, String> {

    Optional<PaymentTransaction> findByPaymentId(String paymentId);

    Optional<PaymentTransaction> findByOrderId(String orderId);
    Optional<PaymentTransaction> findByTransactionId(
            String transactionId);

}
