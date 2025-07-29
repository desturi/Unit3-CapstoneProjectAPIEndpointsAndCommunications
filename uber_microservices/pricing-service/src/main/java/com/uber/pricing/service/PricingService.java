package com.uber.pricing.service;

import com.uber.pricing.dto.BookingPricingRequest;
import com.uber.pricing.dto.PricingResponse;
import org.springframework.stereotype.Service;

/*
 * LESSON NOTE: Simplified Pricing Service
 * - Focuses on basic price calculation functionality
 * - Demonstrates microservice communication pattern
 * - Similar to fare calculation service in cab booking
 */
@Service
public class PricingService {

    // Basic pricing constants
    private static final double BASE_PRICE_PER_PERSON = 12.0;
    private static final double TAX_RATE = 0.08;

    /*
     * LESSON NOTE: Simplified Pricing Calculation
     * - Takes booking details and calculates total price
     * - Demonstrates basic microservice communication pattern
     * - Similar to fare calculation: distance/complexity -> price
     */
    public PricingResponse calculateBookingPrice(BookingPricingRequest request) {
        // Calculate base price based on number of people
        double basePrice = request.getNumberOfPeople() * BASE_PRICE_PER_PERSON;

        // Add complexity factor based on city
        double complexityFactor = calculateComplexityFactor(request.getCity());
        double adjustedPrice = basePrice * complexityFactor;

        // Calculate tax
        double tax = adjustedPrice * TAX_RATE;

        // Calculate total
        double totalAmount = adjustedPrice + tax;

        return new PricingResponse(totalAmount);
    }

    /*
     * LESSON NOTE: Complexity Factor Calculation
     * - Simulates how different car category can affect pricing
     * - Like distance/time affecting cab fare
     */
    private double calculateComplexityFactor(String city) {
        if (city == null || city.trim().isEmpty()) {
            return 1.0;
        }

        // Simple complexity based on car category mentioned
        String location = city.toLowerCase();
        double factor = 1.0;

        if (location.contains("newyork") || location.contains("califorina")) {
            factor += 0.5;
        }
        if (location.contains("seattle") || location.contains("chicago")) {
            factor += 0.3;
        }
        if (location.contains("bentonvil") || location.contains("bostonZ")) {
            factor -= 0.1;
        }

        return Math.max(0.8, Math.min(2.0, factor));
    }
}