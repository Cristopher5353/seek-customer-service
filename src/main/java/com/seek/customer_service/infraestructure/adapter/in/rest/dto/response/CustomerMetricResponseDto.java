package com.seek.customer_service.infraestructure.adapter.in.rest.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerMetricResponseDto {
    private double average;
    private double standardDeviationAge;
}
