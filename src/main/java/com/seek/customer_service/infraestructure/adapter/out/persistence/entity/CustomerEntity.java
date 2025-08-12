package com.seek.customer_service.infraestructure.adapter.out.persistence.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import java.time.LocalDate;

@Table(name = "customers")
@Data
@Builder
public class CustomerEntity {
    @Id
    private Long id;
    private String name;
    private String lastName;
    private Integer age;
    private LocalDate dateOfBirth;
}
