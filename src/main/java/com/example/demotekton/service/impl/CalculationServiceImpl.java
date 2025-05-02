package com.example.demotekton.service.impl;

import com.example.demotekton.service.CalculationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Implementation of the calculation service that sums two numbers and applies an additional percentage
 * obtained from an external service. This implementation uses the `PercentageService` to get the percentage.
 *
 * @author Facundo Cortez
 * @version 1.0
 *
 */

@Service
@AllArgsConstructor
public class CalculationServiceImpl implements CalculationService {

    private final PercentageServiceImpl percentageService;

    /**
     * Sums two numbers and applies an additional percentage to the sum.
     * The percentage is obtained from the `PercentageService`.
     *
     * @author Facundo Cortez
     * @version 1.0
     *
     * @param num1 The first number to add.
     * @param num2 The second number to add.
     * @return The result of the sum with the applied percentage.
     */

    @Override
    public double calculateWithPercentage(double num1, double num2) {

        double sum = num1 + num2;
        double percentage = percentageService.getPorcentage();
        double additionalAmount = (sum * percentage) / 100;

        return sum + additionalAmount;
    }
}
