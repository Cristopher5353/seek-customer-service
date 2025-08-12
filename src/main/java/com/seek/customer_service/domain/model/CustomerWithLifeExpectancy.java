package com.seek.customer_service.domain.model;

public class CustomerWithLifeExpectancy {
    private String name;
    private String lastName;
    private Integer age;
    private String dateOfBirth;
    private String estimatedLifeExpectancyDate;

    public CustomerWithLifeExpectancy() {
    }

    public CustomerWithLifeExpectancy(String name, String lastName, Integer age, String dateOfBirth, String estimatedLifeExpectancyDate) {
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.dateOfBirth = dateOfBirth;
        this.estimatedLifeExpectancyDate = estimatedLifeExpectancyDate;
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

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEstimatedLifeExpectancyDate() {
        return estimatedLifeExpectancyDate;
    }

    public void setEstimatedLifeExpectancyDate(String estimatedLifeExpectancyDate) {
        this.estimatedLifeExpectancyDate = estimatedLifeExpectancyDate;
    }
}
