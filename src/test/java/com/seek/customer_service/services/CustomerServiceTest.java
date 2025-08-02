package com.seek.customer_service.services;

import com.seek.customer_service.dtos.request.CustomerRequestDto;
import com.seek.customer_service.dtos.response.CustomerMetricResponseDto;
import com.seek.customer_service.dtos.response.CustomerResponseDto;
import com.seek.customer_service.dtos.response.CustomerResponseSavedDto;
import com.seek.customer_service.models.Customer;
import com.seek.customer_service.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import java.time.LocalDate;
import java.util.Arrays;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {
    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @Test
    void saveCustomer_shouldReturnSavedCustomerDto() {
        CustomerRequestDto requestDto = new CustomerRequestDto(
                "Cristopher",
                "Coronel",
                21,
                "2003-11-21");
        Customer savedCustomer = Customer.builder()
                .name("Cristopher")
                .lastName("Coronel")
                .age(21)
                .dateOfBirth(LocalDate.of(2003, 11, 21))
                .build();

        when(customerRepository.save(any(Customer.class))).thenReturn(Mono.just(savedCustomer));

        Mono<CustomerResponseSavedDto> result = customerService.saveCustomer(requestDto);

        StepVerifier.create(result)
                .expectNextMatches(dto ->
                        dto.getName().equals("Cristopher") &&
                                dto.getLastName().equals("Coronel") &&
                                dto.getAge() == 21 &&
                                dto.getDateOfBirth().equals("2003-11-21"))
                .verifyComplete();
    }

    @Test
    void getCustomerMetrics_shouldReturnCorrectAverageAndStandardDeviation() {
        Customer customer1 = Customer.builder().age(10).build();
        Customer customer2 = Customer.builder().age(25).build();
        Customer customer3 = Customer.builder().age(32).build();
        Customer customer4 = Customer.builder().age(50).build();

        when(customerRepository.findAll()).thenReturn(Flux.fromIterable(Arrays.asList(customer1, customer2, customer3, customer4)));

        Mono<CustomerMetricResponseDto> result = customerService.getCustomerMetrics();

        StepVerifier.create(result)
                .expectNextMatches(dto ->
                        dto.getAverage() == 29.25 &&
                                dto.getStandardDeviationAge() == 14.38)
                .verifyComplete();
    }

    @Test
    void getAllCustomers_shouldReturnAllWithEstimatedLifeExpectancy() {
        Customer customer = Customer.builder()
                .name("Cristopher")
                .lastName("Coronel")
                .age(21)
                .dateOfBirth(LocalDate.of(2003, 11, 21))
                .build();

        when(customerRepository.findAll()).thenReturn(Flux.just(customer));

        Flux<CustomerResponseDto> result = customerService.getAllCustomers();

        StepVerifier.create(result)
                .expectNextMatches(dto ->
                        dto.getName().equals("Cristopher") &&
                                dto.getEstimatedLifeExpectancyDate().equals("2093-11-21"))
                .verifyComplete();
    }
}
