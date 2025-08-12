package com.seek.customer_service.domain.port.out;

import com.seek.customer_service.domain.model.Customer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerRepositoryPort {
    Mono<Customer> save(Customer customer);
    Flux<Customer> findAll();
}
