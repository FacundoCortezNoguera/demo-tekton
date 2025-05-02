package com.example.demotekton.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * Service that handles caching operations for percentage values using Redis.
 * Provides methods to store and retrieve percentage values with a fixed time-to-live (TTL).
 * The data is stored using a Redis key-value store through {@link RedisTemplate}.
 * This service is used by other components to avoid repeated calls to external services
 * by caching percentage values temporarily.
 *
 * @author Facundo
 * @version 1.0
 */

@Service
@RequiredArgsConstructor
public class CacheService {

    private final RedisTemplate<String, Double> redisTemplate;
    private static final String PERCENTAGE_KEY = "percentage";
    private static final long TTL_MINUTES = 30;

    /**
     * Stores a percentage value in Redis cache with a TTL of 30 minutes.
     *
     * @param percentage the percentage value to cache
     */
    public void savePercentage(double percentage) {
        redisTemplate.opsForValue().set(PERCENTAGE_KEY, percentage, TTL_MINUTES, TimeUnit.MINUTES);
    }

    /**
     * Retrieves the cached percentage value from Redis, if present.
     *
     * @return an {@link Optional} containing the cached value, or empty if not found
     */
    public Optional<Double> getCachedPercentage() {
        Double value = redisTemplate.opsForValue().get(PERCENTAGE_KEY);
        return Optional.ofNullable(value);
    }
}
