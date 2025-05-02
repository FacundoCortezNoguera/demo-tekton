package com.example.demotekton.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CalculationServiceImplTest {

    @Mock
    private PercentageServiceImpl percentageService;

    @InjectMocks
    private CalculationServiceImpl calculationService;


    @Test
    void testCalculateWithPercentage_10percent() {
        // Given
        when(percentageService.getPorcentage()).thenReturn(10.0);

        // When
        double result = calculationService.calculateWithPercentage(100.0, 50.0);

        // Then: (100 + 50) + 10% = 150 + 15 = 165
        assertEquals(165.0, result, 0.0001);
    }

    @Test
    void testCalculateWithPercentage_zeroPercent() {
        when(percentageService.getPorcentage()).thenReturn(0.0);
        double result = calculationService.calculateWithPercentage(20, 30);
        assertEquals(50.0, result, 0.0001);
    }

    @Test
    void testCalculateWithPercentage_negativeNumbers() {
        when(percentageService.getPorcentage()).thenReturn(50.0);
        double result = calculationService.calculateWithPercentage(-10, -10); // -20 + (-20*0.5) = -30
        assertEquals(-30.0, result, 0.0001);
    }

}