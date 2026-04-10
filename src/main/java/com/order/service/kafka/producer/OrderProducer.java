package com.order.service.kafka.producer;

import com.order.service.dto.OrderRequest;
import com.order.service.entity.OutboxEvent;
import com.order.service.enums.OutboxStatus;
import com.order.service.repository.OutboxRepository;
import com.order.service.util.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderProducer {

    private final OutboxRepository outboxRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    private final KafkaTemplate<String, Object> retryKafkaTemplate;

    @Scheduled(fixedDelay = 1000)
    public void publishEvents(OrderRequest orderRequest){

        List<OutboxEvent> events = outboxRepository.findTop50ByStatus(OutboxStatus.NEW);

            for (OutboxEvent event : events) {
                try {
                    kafkaTemplate.send(Constants.ORDER_TOPIC, event.getAggregateId(), event.getPayload());
                    event.setStatus(OutboxStatus.SENT);
                } catch (Exception e) {
                    log.error("Failed to publish event {}: {}", event.getId(), e.getMessage());
                    event.setRetryCount(event.getRetryCount() + 1);

                    if (event.getRetryCount() > 3) {
                        event.setStatus(OutboxStatus.FAILED);
                    }
                }
                outboxRepository.save(event);
            }
    }

    @Scheduled(fixedDelay = 5000)
    public void retryFailedEvents() {

        List<OutboxEvent> failedEvents = outboxRepository.findTop50ByStatus(OutboxStatus.FAILED);

        for (OutboxEvent event : failedEvents) {
            try {
                log.info("Retrying FAILED event {}", event.getId());
                kafkaTemplate.send(Constants.ORDER_TOPIC,
                        event.getAggregateId(),
                        event.getPayload());

                event.setStatus(OutboxStatus.SENT);
            } catch (Exception e) {
                log.error("Retry failed for event {}", event.getId(), e);
                event.setRetryCount(event.getRetryCount() + 1);

                if (event.getRetryCount() >= 2) {
                    log.error("Event permanently failed {}", event.getId());
                    event.setStatus(OutboxStatus.FAILED);
                }
            }
            outboxRepository.save(event);
        }
    }

}
