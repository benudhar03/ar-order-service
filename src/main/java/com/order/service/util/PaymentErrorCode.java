package com.order.service.util;

public final class PaymentErrorCode {

    private PaymentErrorCode() {}

    public static final String PAYMENT_NOT_FOUND = "PAYMENT_NOT_FOUND";

    public static final String INVALID_PAYMENT = "INVALID_PAYMENT";

    public static final String PAYMENT_ALREADY_COMPLETED = "PAYMENT_ALREADY_COMPLETED";

    public static final String REFUND_FAILED = "REFUND_FAILED";

    public static final String UNSUPPORTED_PROVIDER = "UNSUPPORTED_PROVIDER";

    public static final String SIGNATURE_VALIDATION_FAILED = "SIGNATURE_VALIDATION_FAILED";
}