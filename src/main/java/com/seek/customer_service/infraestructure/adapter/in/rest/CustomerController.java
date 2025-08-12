package com.seek.customer_service.infraestructure.adapter.in.rest;

import com.seek.customer_service.domain.port.in.CustomerUseCase;
import com.seek.customer_service.infraestructure.adapter.in.rest.dto.request.CustomerRequestDto;
import com.seek.customer_service.infraestructure.adapter.in.rest.dto.response.CustomerMetricResponseDto;
import com.seek.customer_service.infraestructure.adapter.in.rest.dto.response.CustomerResponseDto;
import com.seek.customer_service.infraestructure.adapter.in.rest.dto.response.CustomerResponseSavedDto;
import com.seek.customer_service.infraestructure.adapter.in.rest.mapper.CustomerDtoMapper;
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
    private final CustomerUseCase customerUseCase;
    private final CustomerDtoMapper customerDtoMapper;

    @PostMapping
    public Mono<ResponseEntity<CustomerResponseSavedDto>> saveCustomer(
            @Valid @RequestBody CustomerRequestDto customerRequestDto) {
        return customerUseCase.saveCustomer(customerDtoMapper.toDomain(customerRequestDto))
                .map(customerSaved -> ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(customerDtoMapper.toCustomerResponseSavedDto(customerSaved)));
    }

    @GetMapping("/metrics")
    public Mono<ResponseEntity<CustomerMetricResponseDto>> getCustomerMetrics() {
        return customerUseCase.getCustomerMetrics()
                .map(customerDtoMapper::toCustomerMetricResponseDto)
                .map(ResponseEntity::ok);
    }

    @GetMapping
    public Flux<CustomerResponseDto> getAllCustomers() {
        return customerUseCase.getAllCustomers()
                .map(customerDtoMapper::toCustomerWithLifeExpectancy);
    }

}
