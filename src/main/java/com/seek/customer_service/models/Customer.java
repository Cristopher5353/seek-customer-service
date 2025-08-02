package com.seek.customer_service.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import java.time.LocalDate;

@Table(name = "customers")
@Data
@Builder
public class Customer {
    @Id
    private Long id;
    private String name;
    private String lastName;
    private Integer age;
    private LocalDate dateOfBirth;
}
