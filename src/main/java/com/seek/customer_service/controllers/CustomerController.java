package com.seek.customer_service.controllers;

import com.seek.customer_service.dtos.request.CustomerRequestDto;
import com.seek.customer_service.dtos.response.CustomerMetricResponseDto;
import com.seek.customer_service.dtos.response.CustomerResponseDto;
import com.seek.customer_service.dtos.response.CustomerResponseSavedDto;
import com.seek.customer_service.services.ICustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final ICustomerService customerService;

    @PostMapping
    public Mono<ResponseEntity<CustomerResponseSavedDto>> saveCustomer(
            @Valid @RequestBody CustomerRequestDto customerRequestDto) {
        return customerService.saveCustomer(customerRequestDto)
                .map(customerSaved -> ResponseEntity.status(HttpStatus.CREATED).body(customerSaved));
    }

    @GetMapping("/metrics")
    public Mono<ResponseEntity<CustomerMetricResponseDto>> getCustomerMetrics() {
        return customerService.getCustomerMetrics()
                .map(ResponseEntity::ok);
    }

    @GetMapping
    public Flux<CustomerResponseDto> getAllCustomers() {
        return customerService.getAllCustomers();
    }
}
