package com.order.service.dto;

import com.order.service.enums.EventStatus;
import lombok.*;

import java.math.BigDecimal;


@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderEvent {

    private String eventId;
    private String orderId;
    private String userId;
    private BigDecimal amount;
    private EventStatus status;
}
