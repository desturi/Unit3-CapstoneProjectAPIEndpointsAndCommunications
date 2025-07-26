package com.uber.gateway.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * LESSON NOTE: Circuit Breaker Fallback Controller
 *
 * When microservices are down or responding slowly, the circuit breaker pattern
 * prevents cascading failures by providing fallback responses instead of letting
 * requests hang or fail completely.
 *
 * Circuit Breaker States:
 * 1. CLOSED: Normal operation, requests flow through
 * 2. OPEN: Service is failing, fallback is used immediately
 * 3. HALF-OPEN: Testing if service has recovered
 *
 * Benefits:
 * - Prevents cascade failures
 * - Improves user experience with meaningful error messages
 * - Gives failing services time to recover
 * - Provides system stability under load
 *
 * This controller provides user-friendly fallback responses for each service.
 */
@RestController
public class FallbackController {

    /**
     * LESSON NOTE: Booking Service Fallback

     * When the Booking Service is down or slow, this fallback provides a
     * user-friendly response instead of a system error.
     */
    @RequestMapping("/fallback/bookings")
    public ResponseEntity<Map<String, Object>> bookingServiceFallback() {
        return createFallbackResponse(
                "Booking Service Temporarily Unavailable",
                "We're experiencing high demand. Please try placing your booking again in a few moments.",
                "BOOKING_SERVICE_DOWN"
        );
    }

    /**
     * LESSON NOTE: Pricing Service Fallback
     *
     * When the Pricing Service is down, booking can't be processed because
     * we can't calculate the total cost. This provides clear feedback to users.
     */
    @RequestMapping("/fallback/pricing")
    public ResponseEntity<Map<String, Object>> pricingServiceFallback() {
        return createFallbackResponse(
                "Pricing Service Temporarily Unavailable",
                "Unable to calculate booking prices right now. Please try again shortly.",
                "PRICING_SERVICE_DOWN"
        );
    }

    /**
     * LESSON NOTE: Standardized Fallback Response
     *
     * Creates a consistent fallback response structure that:
     * - Provides clear error messaging
     * - Includes timestamps for debugging
     * - Uses appropriate HTTP status codes
     * - Gives actionable guidance to users
     */
    private ResponseEntity<Map<String, Object>> createFallbackResponse(String title, String message, String errorCode) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", true);
        response.put("title", title);
        response.put("message", message);
        response.put("errorCode", errorCode);
        response.put("timestamp", LocalDateTime.now());
        response.put("suggestion", "This is a temporary issue. The service will be restored shortly.");

        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
    }
}