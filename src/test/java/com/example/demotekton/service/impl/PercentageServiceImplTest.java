package com.example.demotekton.service.impl;

import com.example.demotekton.service.CacheService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.verification.VerificationMode;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PercentageServiceImplTest {

    @Mock
    private CacheService cacheService;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private PercentageServiceImpl percentageService;

    @Test
    void testGetPercentage_fromCache() {
        when(cacheService.getCachedPercentage()).thenReturn(Optional.of(10.0));

        double result = percentageService.getPorcentage();

        assertEquals(10.0, result, 0.0001);
        verify(cacheService, never()).savePercentage(anyDouble());
        verify(restTemplate, never()).getForObject(anyString(), eq(Double.class));
    }

    @Test
    void testGetPercentage_fromExternalAndSaved() {
        when(cacheService.getCachedPercentage()).thenReturn(Optional.empty());
        when(restTemplate.getForObject(anyString(), eq(Double.class))).thenReturn(12.5);

        double result = percentageService.getPorcentage();

        assertEquals(12.5, result, 0.0001);
        verify(cacheService).savePercentage(12.5);
    }

    @Test
    void testGetPercentage_externalFails_throwsNPE() {
        when(cacheService.getCachedPercentage()).thenReturn(Optional.empty());
        when(restTemplate.getForObject(anyString(), eq(Double.class)))
                .thenThrow(new RuntimeException("Service Down"));

        assertThrows(NullPointerException.class, () -> {
            percentageService.getPorcentage();
        });
    }

}