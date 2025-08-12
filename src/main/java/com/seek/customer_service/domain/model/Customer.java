package com.seek.customer_service.domain.model;

import java.time.LocalDate;

public class Customer {
    private Long id;
    private String name;
    private String lastName;
    private Integer age;
    private LocalDate dateOfBirth;

    public Customer() {
    }

    public Customer(Long id, String name, String lastName, Integer age, LocalDate dateOfBirth) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.dateOfBirth = dateOfBirth;
    }

    public Customer(String name, String lastName, Integer age, LocalDate dateOfBirth) {
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.dateOfBirth = dateOfBirth;
    }

    public Customer(Integer age) {
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
