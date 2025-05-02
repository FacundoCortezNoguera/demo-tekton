package com.example.demotekton.exception;

public class BusinessCalculationException extends RuntimeException {

    private final double num1;
    private final double num2;

    public BusinessCalculationException(String message, double num1, double num2) {
        super(message);
        this.num1 = num1;
        this.num2 = num2;
    }

    public double getNum1() { return num1; }
    public double getNum2() { return num2; }
}
