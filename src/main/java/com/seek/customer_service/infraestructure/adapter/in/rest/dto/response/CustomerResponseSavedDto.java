package com.seek.customer_service.infraestructure.adapter.in.rest.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerResponseSavedDto {
    private String name;
    private String lastName;
    private Integer age;
    private String dateOfBirth;
}
