package com.seek.customer_service.infraestructure.adapter.out.eventbus;

import com.seek.customer_service.domain.port.out.EventBusRepositoryPort;
import com.seek.customer_service.infraestructure.config.eventbus.AzureServiceBusSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/*@Component
@RequiredArgsConstructor
public class EventBusAdapter implements EventBusRepositoryPort {
    private final AzureServiceBusSender azureServiceBusSender;

    @Override
    public Mono<Void> customerCreatedEvent(String message) {
        return azureServiceBusSender.sendMessageAsync(message);
    }
}*/
