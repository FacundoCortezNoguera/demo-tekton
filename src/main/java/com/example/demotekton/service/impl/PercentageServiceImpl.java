package com.example.demotekton.service.impl;

import com.example.demotekton.service.CacheService;
import com.example.demotekton.service.PercentageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Service that provides a fixed percentage (simulating a call to an external service).
 * This service is used to provide the percentage applied in `CalculationService`.
 * @author Facundo Cortez
 * @version 1.0
 */

@Service
@RequiredArgsConstructor
public class PercentageServiceImpl implements PercentageService {

    private final CacheService cacheService;
    private final RestTemplate restTemplate;


    /**
     * Simulates the retrieval of a percentage from an external service.
     *
     * @author Facundo Cortez
     * @version 1.0
     *
     * @return A fixed percentage of 1% to 100%.
     */

    private Double getExternalPercentage() {
        String url = "http://localhost:8080/external/percentage";
        try {
            return restTemplate.getForObject(url, Double.class);
        } catch (Exception e) {
            // Podés loguear si querés
            System.out.println("Error al llamar al servicio externo: " + e.getMessage());
            return null;
        }
    }

    /**
     * Service that provides a fixed percentage (simulating a call to an external service).
     * This service is used to provide the percentage applied in CalculationService.
     *
     * Retrieves the percentage from cache when available,
     * otherwise it simulates a call to an external service and caches the result.
     *
     * @author Facundo Cortez
     * @version 1.0
     *
     * @return A fixed percentage of 1% to 100%.
     */

    @Override
    public double getPorcentage() {

        return cacheService.getCachedPercentage()
                .orElseGet(() -> {
                    double external = getExternalPercentage();
                    cacheService.savePercentage(external);
                    return external;
                });
    }
}
