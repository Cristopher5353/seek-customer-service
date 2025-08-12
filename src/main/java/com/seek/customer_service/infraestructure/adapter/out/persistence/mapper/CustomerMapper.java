package com.seek.customer_service.infraestructure.adapter.out.persistence.mapper;

import com.seek.customer_service.domain.model.Customer;
import com.seek.customer_service.infraestructure.adapter.out.persistence.entity.CustomerEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    Customer toDomain(CustomerEntity entity);
    CustomerEntity toEntity(Customer customer);
}
