package com.example.demotekton.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CacheServiceTest {

    @Mock
    private RedisTemplate<String, Double> redisTemplate;

    @Mock
    private ValueOperations<String, Double> valueOperations;

    @InjectMocks
    private CacheService cacheService;

    @Test
    void testSavePercentage_shouldStoreValueWithTTL() {
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);

        cacheService.savePercentage(12.5);

        verify(valueOperations).set("percentage", 12.5, 30, TimeUnit.MINUTES);
    }

    @Test
    void testGetCachedPercentage_whenValueExists_shouldReturnIt() {
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.get("percentage")).thenReturn(10.0);

        Optional<Double> result = cacheService.getCachedPercentage();

        assertTrue(result.isPresent());
        assertEquals(10.0, result.get());
    }

    @Test
    void testGetCachedPercentage_whenValueIsNull_shouldReturnEmptyOptional() {
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.get("percentage")).thenReturn(null);

        Optional<Double> result = cacheService.getCachedPercentage();

        assertTrue(result.isEmpty());
    }

}