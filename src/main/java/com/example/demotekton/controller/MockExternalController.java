package com.example.demotekton.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * Mock controller that simulates an external service returning a percentage value.
 * This controller is typically used for testing purposes, returning a random percentage value
 * between 1 and 101 to mimic an external service response.
 *
 * @author Facundo Cortez
 * @version 1.0
 */
@RestController
@RequestMapping("/external")
public class MockExternalController {


    /**
     * Endpoint that returns a random percentage value between 1 and 101.
     *
     * @return a {@link ResponseEntity} containing a {@link Double} percentage value
     */
    @GetMapping("/percentage")
    public ResponseEntity<Double> getPercentage() {
        return ResponseEntity.ok( (new Random().nextDouble(100) + 1));
    }

}
