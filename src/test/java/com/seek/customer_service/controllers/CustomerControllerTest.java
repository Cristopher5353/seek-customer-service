package com.seek.customer_service.controllers;

import com.seek.customer_service.domain.model.Customer;
import com.seek.customer_service.domain.model.CustomerMetric;
import com.seek.customer_service.domain.model.CustomerWithLifeExpectancy;
import com.seek.customer_service.domain.port.in.CustomerUseCase;
import com.seek.customer_service.infraestructure.adapter.in.rest.CustomerController;
import com.seek.customer_service.infraestructure.adapter.in.rest.dto.request.CustomerRequestDto;
import com.seek.customer_service.infraestructure.adapter.in.rest.dto.response.CustomerMetricResponseDto;
import com.seek.customer_service.infraestructure.adapter.in.rest.dto.response.CustomerResponseDto;
import com.seek.customer_service.infraestructure.adapter.in.rest.dto.response.CustomerResponseSavedDto;
import com.seek.customer_service.infraestructure.adapter.in.rest.mapper.CustomerDtoMapper;
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

import java.time.LocalDate;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

    @Mock
    private CustomerUseCase customerUseCase;

    @Mock
    private CustomerDtoMapper customerDtoMapper;

    @InjectMocks
    private CustomerController customerController;

    private WebTestClient webTestClient;
    private CustomerRequestDto requestDto;
    private Customer domainCustomer;
    private CustomerResponseSavedDto responseSavedDto;
    private CustomerMetricResponseDto metricResponseDto;
    private CustomerResponseDto customerResponseDto;

    @BeforeEach
    void setup() {
        webTestClient = WebTestClient.bindToController(customerController).build();

        requestDto = new CustomerRequestDto("Cristopher", "Coronel", 21, "2003-11-21");
        domainCustomer = new Customer(null, "Cristopher", "Coronel", 21, LocalDate.parse("2003-11-21"));
        responseSavedDto = CustomerResponseSavedDto.builder()
                .name("Cristopher")
                .lastName("Coronel")
                .age(21)
                .dateOfBirth("2003-11-21")
                .build();
        metricResponseDto = CustomerMetricResponseDto.builder()
                .average(10L)
                .standardDeviationAge(5L)
                .build();
        customerResponseDto = CustomerResponseDto
                .builder()
                .name("Cristopher")
                .lastName("Coronel")
                .age(21)
                .dateOfBirth("2003-11-21")
                .estimatedLifeExpectancyDate("2093")
                .build();
    }

    @Test
    void testSaveCustomer() {
        Mockito.when(customerDtoMapper.toDomain(requestDto)).thenReturn(domainCustomer);
        Mockito.when(customerUseCase.saveCustomer(domainCustomer)).thenReturn(Mono.just(domainCustomer));
        Mockito.when(customerDtoMapper.toCustomerResponseSavedDto(domainCustomer)).thenReturn(responseSavedDto);

        webTestClient.post()
                .uri("/api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestDto)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(CustomerResponseSavedDto.class)
                .isEqualTo(responseSavedDto);
    }

    @Test
    void testGetCustomerMetrics() {
        Mockito.when(customerUseCase.getCustomerMetrics()).thenReturn(Mono.just(new CustomerMetric(10L, 5L)));
        Mockito.when(customerDtoMapper.toCustomerMetricResponseDto(any())).thenReturn(metricResponseDto);

        webTestClient.get()
                .uri("/api/v1/customers/metrics")
                .exchange()
                .expectStatus().isOk()
                .expectBody(CustomerMetricResponseDto.class)
                .isEqualTo(metricResponseDto);
    }

    @Test
    void testGetAllCustomers() {
        Mockito.when(customerUseCase.getAllCustomers()).thenReturn(Flux.just(new CustomerWithLifeExpectancy("Cristopher", "Coronel", 21, "2003-11-21", "2093")));
        Mockito.when(customerDtoMapper.toCustomerWithLifeExpectancy(any())).thenReturn(customerResponseDto);

        webTestClient.get()
                .uri("/api/v1/customers")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(CustomerResponseDto.class)
                .isEqualTo(List.of(customerResponseDto));
    }
}

