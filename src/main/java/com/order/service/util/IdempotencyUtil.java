package com.order.service.util;

import java.util.UUID;

public class IdempotencyUtil {

    public static String generateOrderId() {
        return UUID.randomUUID().toString();
    }

    public static String generateProductId() {

        return "PRD-"
                + UUID.randomUUID()
                .toString()
                .substring(0, 8)
                .toUpperCase();
    }
}
