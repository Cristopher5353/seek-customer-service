package com.seek.customer_service.repositories;

import com.seek.customer_service.models.Customer;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface CustomerRepository extends R2dbcRepository<Customer, Long> {
}
