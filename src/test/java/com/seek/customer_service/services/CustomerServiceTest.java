package com.seek.customer_service.services;

import com.seek.customer_service.application.CustomerServiceImpl;
import com.seek.customer_service.domain.model.Customer;
import com.seek.customer_service.domain.model.CustomerMetric;
import com.seek.customer_service.domain.model.CustomerWithLifeExpectancy;
import com.seek.customer_service.domain.port.in.CustomerUseCase;
import com.seek.customer_service.domain.port.out.CustomerRepositoryPort;
import com.seek.customer_service.domain.port.out.EventBusRepositoryPort;
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
    private CustomerRepositoryPort customerRepository;

    @Mock
    private EventBusRepositoryPort eventBusRepositoryPort;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Test
    void saveCustomer_shouldReturnSavedCustomerDto() {
        Customer requestDto = new Customer(
                "Cristopher",
                "Coronel",
                21,
                LocalDate.parse("2003-11-21"));
        Customer savedCustomer = new Customer(
                1L,
                "Cristopher",
                "Coronel",
                21,
                LocalDate.parse("2003-11-21"));

        when(customerRepository.save(any(Customer.class))).thenReturn(Mono.just(savedCustomer));
        when(eventBusRepositoryPort.customerCreatedEvent(anyString())).thenReturn(Mono.empty());

        Mono<Customer> result = customerService.saveCustomer(requestDto);

        StepVerifier.create(result)
                .expectNextMatches(dto ->
                        dto.getName().equals("Cristopher") &&
                                dto.getLastName().equals("Coronel") &&
                                dto.getAge() == 21 &&
                                dto.getDateOfBirth().equals(LocalDate.parse("2003-11-21")))
                .verifyComplete();
    }

    @Test
    void getCustomerMetrics_shouldReturnCorrectAverageAndStandardDeviation() {
        Customer customer1 = new Customer(10);
        Customer customer2 = new Customer(25);
        Customer customer3 = new Customer(32);
        Customer customer4 = new Customer(50);

        when(customerRepository.findAll()).thenReturn(Flux.fromIterable(Arrays.asList(customer1, customer2, customer3, customer4)));

        Mono<CustomerMetric> result = customerService.getCustomerMetrics();

        StepVerifier.create(result)
                .expectNextMatches(dto ->
                        dto.getAverage() == 29.25 &&
                                dto.getStandardDeviationAge() == 14.38)
                .verifyComplete();
    }

    @Test
    void getAllCustomers_shouldReturnAllWithEstimatedLifeExpectancy() {
        Customer customer = new Customer(
                "Cristopher",
                "Coronel",
                21,
                LocalDate.parse("2003-11-21"));

        when(customerRepository.findAll()).thenReturn(Flux.just(customer));

        Flux<CustomerWithLifeExpectancy> result = customerService.getAllCustomers();

        StepVerifier.create(result)
                .expectNextMatches(dto ->
                        dto.getName().equals("Cristopher") &&
                                dto.getEstimatedLifeExpectancyDate().equals("2093-11-21"))
                .verifyComplete();
    }
}
