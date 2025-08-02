package com.seek.customer_service.dtos.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerResponseDto {
    private String name;
    private String lastName;
    private Integer age;
    private String dateOfBirth;
    private String estimatedLifeExpectancyDate;
}
