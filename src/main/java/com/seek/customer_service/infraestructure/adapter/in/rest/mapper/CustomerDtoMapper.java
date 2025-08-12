package com.seek.customer_service.infraestructure.adapter.in.rest.mapper;

import com.seek.customer_service.domain.model.Customer;
import com.seek.customer_service.domain.model.CustomerMetric;
import com.seek.customer_service.domain.model.CustomerWithLifeExpectancy;
import com.seek.customer_service.infraestructure.adapter.in.rest.dto.request.CustomerRequestDto;
import com.seek.customer_service.infraestructure.adapter.in.rest.dto.response.CustomerMetricResponseDto;
import com.seek.customer_service.infraestructure.adapter.in.rest.dto.response.CustomerResponseDto;
import com.seek.customer_service.infraestructure.adapter.in.rest.dto.response.CustomerResponseSavedDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerDtoMapper {
    Customer toDomain(CustomerRequestDto customerRequestDto);
    CustomerResponseSavedDto toCustomerResponseSavedDto(Customer customer);
    CustomerMetricResponseDto toCustomerMetricResponseDto(CustomerMetric customerMetric);
    CustomerResponseDto toCustomerWithLifeExpectancy(CustomerWithLifeExpectancy customerResponseDto);
}
