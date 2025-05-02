package com.example.demotekton.service;


/**
 * Interface that defines the methods for performing calculations with an additional percentage.
 * The percentage is obtained from an external service.
 *
 * @author Facundo Cortez
 * @version 1.0
 */
public interface CalculationService {

    double calculateWithPercentage(double num1, double num2);

}
