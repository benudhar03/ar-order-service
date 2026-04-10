package com.order.service.kafka.consumer;

import com.order.service.dto.OrderEvent;
import com.order.service.entity.Order;
import com.order.service.enums.OrderStatus;
import com.order.service.repository.OrderRepository;
import com.order.service.util.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;


@RequiredArgsConstructor
@Component
@Slf4j
public class OrderConsumer {

    private final OrderRepository repository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @KafkaListener(topics = Constants.ORDER_TOPIC, containerFactory = "batchFactory")
    public void consume(List<OrderEvent> events, Acknowledgment ack) {

        for (OrderEvent event : events) {
            try {
                log.info("Processing order {}", event.getOrderId());
                if (repository.findByOrderId(event.getOrderId()).isPresent()) {
                    log.warn("Duplicate order ignored: {}", event.getOrderId());
                    continue;
                }
                Order order = Order.builder()
                        .orderId(event.getOrderId())
                        .userId(event.getUserId())
                        .amount(event.getAmount())
                        .status(OrderStatus.CREATED)
                        .createdAt(Instant.now())
                        .updatedAt(Instant.now())
                        .build();
                repository.save(order);

            } catch (Exception ex) {
                log.error("Error processing order {}", event.getOrderId(), ex);
                kafkaTemplate.send(Constants.DLQ_TOPIC, event.getOrderId(), event);
            }
        }
        ack.acknowledge();
    }

}
