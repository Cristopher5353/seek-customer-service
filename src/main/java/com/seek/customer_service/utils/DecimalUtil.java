package com.seek.customer_service.utils;

public class DecimalUtil {
    public static double roundToTwoDecimals(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
