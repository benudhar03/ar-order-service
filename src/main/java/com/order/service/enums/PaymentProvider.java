package com.order.service.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentProvider {

    RAZORPAY("RAZORPAY", "Razorpay"),
    STRIPE("STRIPE", "Stripe"),
    PHONEPE("PHONEPE", "PhonePe"),
    PAYTM("PAYTM", "Paytm"),
    CASHFREE("CASHFREE", "Cashfree"),
    PAYPAL("PAYPAL", "PayPal");

    private final String code;

    private final String displayName;
}