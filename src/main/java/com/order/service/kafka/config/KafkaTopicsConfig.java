package com.order.service.kafka.config;

import com.order.service.util.Constants;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicsConfig {

    @Bean
    public NewTopic ordersTopic() {
        return new NewTopic(Constants.ORDER_TOPIC, 3, (short) 1);
    }

    @Bean
    public NewTopic dlqTopic() {
        return new NewTopic(Constants.DLQ_TOPIC, 3, (short) 1);
    }
}
