package com.seek.customer_service.application;

import com.seek.customer_service.application.utils.ConstantsUtil;
import com.seek.customer_service.application.utils.DecimalUtil;
import com.seek.customer_service.domain.model.Customer;
import com.seek.customer_service.domain.model.CustomerMetric;
import com.seek.customer_service.domain.model.CustomerWithLifeExpectancy;
import com.seek.customer_service.domain.port.in.CustomerUseCase;
import com.seek.customer_service.domain.port.out.CustomerRepositoryPort;
import com.seek.customer_service.domain.port.out.EventBusRepositoryPort;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.time.LocalDate;

public class CustomerServiceImpl implements CustomerUseCase {
    private final CustomerRepositoryPort customerRepositoryPort;
    //private final EventBusRepositoryPort eventBusRepositoryPort;

    public CustomerServiceImpl(CustomerRepositoryPort customerRepositoryPort
                               /*EventBusRepositoryPort eventBusRepositoryPort*/) {
        this.customerRepositoryPort = customerRepositoryPort;
        //this.eventBusRepositoryPort = eventBusRepositoryPort;
    }

    @Override
    public Mono<Customer> saveCustomer(Customer customer) {
        return customerRepositoryPort.save(customer);
                /*.flatMap(customerSaved -> eventBusRepositoryPort.customerCreatedEvent(customerSaved.toString())
                        .thenReturn(customerSaved));*/
    }

    @Override
    public Mono<CustomerMetric> getCustomerMetrics() {
        return customerRepositoryPort.findAll()
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

                    return new CustomerMetric(
                            DecimalUtil.roundToTwoDecimals(average),
                            DecimalUtil.roundToTwoDecimals(standardDeviationAge)
                    );
                });
    }

    @Override
    public Flux<CustomerWithLifeExpectancy> getAllCustomers() {
        return customerRepositoryPort.findAll().map(customer -> {
            LocalDate estimatedLifeExpectancyDate = customer
                    .getDateOfBirth()
                    .plusYears(ConstantsUtil.LIFE_EXPECTANCY_YEARS);

            return new CustomerWithLifeExpectancy(
                    customer.getName(),
                    customer.getLastName(),
                    customer.getAge(),
                    customer.getDateOfBirth().toString(),
                    estimatedLifeExpectancyDate.toString()
            );
        });
    }
}
