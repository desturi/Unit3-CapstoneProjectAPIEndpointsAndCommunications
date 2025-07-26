package com.uber.pricing.controller;

import com.uber.pricing.dto.BookingPricingRequest;
import com.uber.pricing.dto.PricingResponse;
import com.uber.pricing.service.PricingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.valid;

/*
 * LESSON NOTE: Simplified Pricing Controller
 * - Focuses on core pricing calculation functionality
 * - Demonstrates microservice communication pattern
 * - Similar to "calculate fare" functionality in capstone
 */
@RestController
@RequestMapping("/api/pricing")
@Validated
public class PricingController {

    private final PricingService pricingService;

    @Autowired
    public PricingController(PricingService pricingService) {
        this.pricingService = pricingService;
    }

    /*
     * LESSON NOTE: Main Pricing Endpoint
     * - POST /api/pricing/calculate
     * - This is the core endpoint that other microservices will call
     * - @Valid annotation triggers validation on the request body
     * - Returns pricing calculation (like fare calculation)
     */
    @PostMapping("/calculate")
    public ResponseEntity<PricingResponse> calculateBookingPrice(@Valid @RequestBody BookingPricingRequest request) {
        try {
            PricingResponse response = pricingService.calculateBookingPrice(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.err.println("Error calculating price: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    /*
     * LESSON NOTE: Health Check Endpoint
     * - Simple endpoint to verify service is running
     * - Important for microservices deployment and orchestration
     */
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Pricing Service is running!");
    }
}