package com.seek.customer_service.domain.model;

public class CustomerMetric {
    private double average;
    private double standardDeviationAge;

    public CustomerMetric() {
    }

    public CustomerMetric(double average, double standardDeviationAge) {
        this.average = average;
        this.standardDeviationAge = standardDeviationAge;
    }

    public double getAverage() {
        return average;
    }

    public void setAverage(double average) {
        this.average = average;
    }

    public double getStandardDeviationAge() {
        return standardDeviationAge;
    }

    public void setStandardDeviationAge(double standardDeviationAge) {
        this.standardDeviationAge = standardDeviationAge;
    }
}
