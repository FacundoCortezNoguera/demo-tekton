package com.example.demotekton.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MockExternalControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void getPercentage_shouldReturnRandomDouble() {
        ResponseEntity<Double> response = restTemplate.getForEntity("/external/percentage", Double.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        double percentage = response.getBody();
        assertTrue(percentage >= 1.0 && percentage <= 101.0, "Percentage should be between 1 and 101");
    }

}