package com.seek.customer_service.controllers;

import com.seek.customer_service.dtos.request.CustomerRequestDto;
import com.seek.customer_service.dtos.response.CustomerMetricResponseDto;
import com.seek.customer_service.dtos.response.CustomerResponseDto;
import com.seek.customer_service.dtos.response.CustomerResponseSavedDto;
import com.seek.customer_service.services.ICustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {
    @Mock
    private ICustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    private WebTestClient webTestClient;
    private CustomerRequestDto requestDto;
    private CustomerResponseSavedDto savedDto;
    private CustomerMetricResponseDto metricDto;
    private CustomerResponseDto responseDto;

    @BeforeEach
    void setup() {
        webTestClient = WebTestClient.bindToController(customerController).build();
        requestDto = new CustomerRequestDto("Cristopher", "Coronel", 21, "2003-11-21");
        savedDto = CustomerResponseSavedDto.builder()
                .name("Cristopher")
                .lastName("Coronel")
                .age(21)
                .dateOfBirth("2003-11-21")
                .build();
        metricDto = CustomerMetricResponseDto
                .builder()
                .average(10L)
                .standardDeviationAge(5L)
                .build();
        responseDto = CustomerResponseDto
                .builder()
                .name("Cristopher")
                .lastName("Coronel")
                .age(21)
                .dateOfBirth("2003-21-11")
                .estimatedLifeExpectancyDate("2093")
                .build();
    }

    @Test
    void testSaveCustomer() {
        Mockito.when(customerService.saveCustomer(any())).thenReturn(Mono.just(savedDto));

        webTestClient.post()
                .uri("/api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestDto)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(CustomerResponseSavedDto.class)
                .isEqualTo(savedDto);
    }

    @Test
    void testGetCustomerMetrics() {
        Mockito.when(customerService.getCustomerMetrics()).thenReturn(Mono.just(metricDto));

        webTestClient.get()
                .uri("/api/v1/customers/metrics")
                .exchange()
                .expectStatus().isOk()
                .expectBody(CustomerMetricResponseDto.class)
                .isEqualTo(metricDto);
    }

    @Test
    void testGetAllCustomers() {
        List<CustomerResponseDto> customers = List.of(responseDto);
        Mockito.when(customerService.getAllCustomers()).thenReturn(Flux.fromIterable(customers));

        webTestClient.get()
                .uri("/api/v1/customers")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(CustomerResponseDto.class)
                .isEqualTo(customers);
    }
}
