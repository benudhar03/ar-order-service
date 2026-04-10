package com.order.service.repository;

import com.order.service.entity.OutboxEvent;
import com.order.service.enums.OutboxStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OutboxRepository extends MongoRepository<OutboxEvent, String> {
    List<OutboxEvent> findTop50ByStatus(OutboxStatus status);
}