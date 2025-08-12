package com.seek.customer_service.domain.port.in;

import com.seek.customer_service.domain.model.Customer;
import com.seek.customer_service.domain.model.CustomerMetric;
import com.seek.customer_service.domain.model.CustomerWithLifeExpectancy;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerUseCase {
    Mono<Customer> saveCustomer(Customer customer);
    Mono<CustomerMetric> getCustomerMetrics();
    Flux<CustomerWithLifeExpectancy> getAllCustomers();
}
