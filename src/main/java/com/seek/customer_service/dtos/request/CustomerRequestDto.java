package com.seek.customer_service.dtos.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequestDto {
    @NotBlank(message = "Name must not be blank")
    private String name;

    @NotBlank(message = "Last name must not be blank")
    private String lastName;

    @NotNull(message = "Age is required")
    @Min(value = 1, message = "Invalid age")
    private Integer age;

    @NotBlank(message = "Date of birth must not be blank")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Date of birth must be in the format yyyy-MM-dd")
    private String dateOfBirth;
}
