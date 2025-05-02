package com.example.demotekton.controller;

import com.example.demotekton.entity.CallLog;
import com.example.demotekton.exception.BusinessCalculationException;
import com.example.demotekton.service.CalculationService;
import com.example.demotekton.service.CallLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Controller that exposes an endpoint for calculating the sum of two numbers
 * and applying an additional percentage. The calculation is performed through the `CalculationService`.
 *
 * @author Facundo Cortez
 * @version 1.0
 */

@RestController
@RequiredArgsConstructor
public class CalculationController {

    private final CalculationService calculationService;
    private final CallLogService callLogService;

    /**
     * Endpoint that receives two numbers as parameters, sums them, and applies an additional percentage
     * through the `CalculationService`.
     *
     * @author Facundo Cortez
     * @version 1.0
     *
     * @param num1 The first number to add.
     * @param num2 The second number to add.
     * @return The result of the sum with the applied percentage.
     */
    @GetMapping("/calculate")
    public ResponseEntity<Double> calculate(@RequestParam double num1, @RequestParam double num2) {
        try {
            double result = calculationService.calculateWithPercentage(num1, num2);

            // REGISTRAR LLAMADA EXITOSA
            callLogService.log(CallLog.builder()
                    .timestamp(LocalDateTime.now())
                    .endpoint("/calculate")
                    .parameters("num1=" + num1 + ", num2=" + num2)
                    .response(String.valueOf(result))
                    .error(false)
                    .build());

            return ResponseEntity.ok(result);

        } catch (Exception e) {
            throw new BusinessCalculationException("Error al calcular porcentaje", num1, num2);

        }
    }

    /**
     * Endpoint that returns the complete list of logged calls,
     * including both successful and failed requests.
     *
     * @author Facundo Cortez
     * @version 1.0
     *
     * @return A list of {@link CallLog} objects stored in the database.
     */
    @GetMapping("/logs")
    public ResponseEntity<List<CallLog>> getLogs() {
        return ResponseEntity.ok(callLogService.getAllCallLogs());
    }

}
