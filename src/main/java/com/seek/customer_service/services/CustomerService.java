package com.seek.customer_service.services;

import com.seek.customer_service.dtos.request.CustomerRequestDto;
import com.seek.customer_service.dtos.response.CustomerMetricResponseDto;
import com.seek.customer_service.dtos.response.CustomerResponseDto;
import com.seek.customer_service.dtos.response.CustomerResponseSavedDto;
import com.seek.customer_service.models.Customer;
import com.seek.customer_service.repositories.CustomerRepository;
import com.seek.customer_service.utils.ConstantsUtil;
import com.seek.customer_service.utils.DecimalUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class CustomerService implements ICustomerService {
    private final CustomerRepository customerRepository;

    @Override
    public Mono<CustomerResponseSavedDto> saveCustomer(CustomerRequestDto customerRequestDto) {
        Customer customer = Customer.builder()
                .name(customerRequestDto.getName())
                .lastName(customerRequestDto.getLastName())
                .age(customerRequestDto.getAge())
                .dateOfBirth(LocalDate.parse(customerRequestDto.getDateOfBirth()))
                .build();
        return customerRepository.save(customer)
                .map(customerSaved -> CustomerResponseSavedDto
                        .builder()
                        .name(customer.getName())
                        .lastName(customer.getLastName())
                        .age(customer.getAge())
                        .dateOfBirth(customer.getDateOfBirth().toString())
                        .build());
    }

    @Override
    public Mono<CustomerMetricResponseDto> getCustomerMetrics() {
        return customerRepository.findAll()
                .map(Customer::getAge)
                .collectList()
                .map(ages -> {
                    double average = ages.stream().mapToInt(Integer::intValue).average().orElse(0.0);
                    double standardDeviationAge = Math.sqrt(
                            ages.stream()
                                    .mapToDouble(age -> Math.pow(age - average, 2))
                                    .average()
                                    .orElse(0.0)
                    );

                    return CustomerMetricResponseDto.builder()
                            .average(DecimalUtil.roundToTwoDecimals(average))
                            .standardDeviationAge(DecimalUtil.roundToTwoDecimals(standardDeviationAge))
                            .build();
                });
    }

    @Override
    public Flux<CustomerResponseDto> getAllCustomers() {
        return customerRepository.findAll().map(customer -> {
            LocalDate estimatedLifeExpectancyDate = customer
                    .getDateOfBirth()
                    .plusYears(ConstantsUtil.LIFE_EXPECTANCY_YEARS);

            return CustomerResponseDto.builder()
                    .name(customer.getName())
                    .lastName(customer.getLastName())
                    .age(customer.getAge())
                    .dateOfBirth(customer.getDateOfBirth().toString())
                    .estimatedLifeExpectancyDate(estimatedLifeExpectancyDate.toString())
                    .build();
        });
    }
}
