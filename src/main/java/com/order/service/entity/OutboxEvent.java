package com.order.service.entity;


import com.order.service.enums.OutboxStatus;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "outbox_events")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OutboxEvent {

    @Id
    private String id;

    private String aggregateId;
    private String eventType;
    private String payload;

    private OutboxStatus status;
    private int retryCount;
    private Instant createdAt;
}