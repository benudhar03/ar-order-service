package com.order.service.strategy;


import com.order.service.enums.PaymentProvider;
import com.order.service.exception.PaymentException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentStrategyFactory {

    private final List<PaymentGatewayStrategy> strategies;

    private final Map<PaymentProvider, PaymentGatewayStrategy> strategyMap = new EnumMap<>(PaymentProvider.class);

    @PostConstruct
    public void init() {
        strategies.forEach(strategy ->
                strategyMap.put(strategy.getProvider(), strategy));
    }

    public PaymentGatewayStrategy getStrategy(PaymentProvider provider) {

        PaymentGatewayStrategy strategy = strategyMap.get(provider);
        if (strategy == null) {
            throw new PaymentException("Unsupported payment provider: " + provider);
        }
        return strategy;
    }

}