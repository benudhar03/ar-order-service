package com.order.service.util;

import java.util.UUID;

public class IdempotencyUtil {

    public static String generateOrderId() {
        return UUID.randomUUID().toString();
    }
}
