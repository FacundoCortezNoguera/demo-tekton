package com.example.demotekton.controller;

import com.example.demotekton.entity.CallLog;
import com.example.demotekton.exception.BusinessCalculationException;
import com.example.demotekton.service.CalculationService;
import com.example.demotekton.service.CallLogService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CalculationControllerTest {

    @Mock
    private CalculationService calculationService;

    @Mock
    private CallLogService callLogService;

    @InjectMocks
    private CalculationController controller;

    @Test
    void calculate_shouldReturnResultAndLogCall() {
        double num1 = 5.0;
        double num2 = 10.0;
        double expectedResult = 16.5;

        when(calculationService.calculateWithPercentage(num1, num2)).thenReturn(expectedResult);

        ResponseEntity<Double> response = controller.calculate(num1, num2);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResult, response.getBody());

        verify(callLogService).log(argThat(log ->
                log.getParameters().equals("num1=5.0, num2=10.0") &&
                        log.getEndpoint().equals("/calculate") &&
                        log.getResponse().equals("16.5") &&
                        !log.isError()
        ));
    }

    @Test
    void calculate_whenServiceThrows_shouldThrowBusinessCalculationException() {
        double num1 = 3.0;
        double num2 = 4.0;

        when(calculationService.calculateWithPercentage(num1, num2))
                .thenThrow(new RuntimeException("Boom"));

        BusinessCalculationException ex = assertThrows(
                BusinessCalculationException.class,
                () -> controller.calculate(num1, num2)
        );

        assertEquals("Error al calcular porcentaje", ex.getMessage());
        assertEquals(num1, ex.getNum1());
        assertEquals(num2, ex.getNum2());
    }

    @Test
    void getLogs_shouldReturnAllCallLogs() {
        List<CallLog> mockLogs = List.of(
                CallLog.builder().endpoint("/calculate").parameters("num1=1,num2=2").response("3.3").error(false).build()
        );

        when(callLogService.getAllCallLogs()).thenReturn(mockLogs);

        ResponseEntity<List<CallLog>> response = controller.getLogs();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockLogs, response.getBody());
    }
}