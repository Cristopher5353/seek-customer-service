package com.seek.customer_service.services;

import com.seek.customer_service.dtos.request.CustomerRequestDto;
import com.seek.customer_service.dtos.response.CustomerMetricResponseDto;
import com.seek.customer_service.dtos.response.CustomerResponseDto;
import com.seek.customer_service.dtos.response.CustomerResponseSavedDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICustomerService {
    Mono<CustomerResponseSavedDto> saveCustomer(CustomerRequestDto customerRequestDto);
    Mono<CustomerMetricResponseDto> getCustomerMetrics();
    Flux<CustomerResponseDto> getAllCustomers();
}
