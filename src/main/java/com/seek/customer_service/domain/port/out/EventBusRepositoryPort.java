package com.seek.customer_service.domain.port.out;

import reactor.core.publisher.Mono;

public interface EventBusRepositoryPort {
    Mono<Void> customerCreatedEvent(String message);
}
