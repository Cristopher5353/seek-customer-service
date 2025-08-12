package com.seek.customer_service.infraestructure.adapter.out.persistence.repository;

import com.seek.customer_service.domain.model.Customer;
import com.seek.customer_service.domain.port.out.CustomerRepositoryPort;
import com.seek.customer_service.infraestructure.adapter.out.persistence.mapper.CustomerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CustomerRepositoryAdapter implements CustomerRepositoryPort {
    private final R2dbcCustomerRepository r2dbcCustomerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public Mono<Customer> save(Customer customer) {
        return r2dbcCustomerRepository.save(customerMapper.toEntity(customer))
                .map(customerMapper::toDomain);
    }

    @Override
    public Flux<Customer> findAll() {
        return r2dbcCustomerRepository.findAll()
                .map(customerMapper::toDomain);
    }
}
