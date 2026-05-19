package com.order.service.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentInitiateRequest {


    private String orderId;

    @NotBlank
    private String customerId;

    @NotNull
    @DecimalMin(value = "1.0")
    private BigDecimal amount;

    @NotBlank
    private String currency;

    @NotBlank
    private String paymentFor;

    /**
     * PHONEPE
     * GPAY
     * PAYTM
     * CREDIT_CARD
     * DEBIT_CARD
     */
    @NotBlank
    private String paymentMethod;

    /**
     * RAZORPAY
     * STRIPE
     * CASHFREE
     */
    @NotBlank
    private String paymentProvider;

    /**
     * Product/cart details
     * Optional for non-commerce payments
     */
    @Valid
    private List<ProductPaymentItem> items;

    private BigDecimal taxAmount;
    private BigDecimal discountAmount;
    private BigDecimal shippingCharges;
    private String couponCode;
    private Map<String, Object> metadata;
    private String successUrl;
    private String failureUrl;
}