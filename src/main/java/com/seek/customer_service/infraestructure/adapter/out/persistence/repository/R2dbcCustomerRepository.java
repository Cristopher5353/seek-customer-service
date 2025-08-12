package com.seek.customer_service.infraestructure.adapter.out.persistence.repository;

import com.seek.customer_service.infraestructure.adapter.out.persistence.entity.CustomerEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface R2dbcCustomerRepository extends R2dbcRepository<CustomerEntity, Long> {
}
