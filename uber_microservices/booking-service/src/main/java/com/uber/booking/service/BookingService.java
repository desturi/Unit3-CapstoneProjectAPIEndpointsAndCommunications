package com.uber.booking.service;

import com.uber.booking.dto.BookingRequest;
import com.uber.booking.dto.BookingResponse;
import com.uber.booking.dto.BookingPricingRequest;
import com.uber.booking.dto.PricingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestClientException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class BookingService {

    private final RestTemplate restTemplate;
    // Thread-safe map to store bookings; allows safe concurrent access by multiple threads
    private final Map<Long, BookingResponse> bookings = new ConcurrentHashMap<>();
    private final AtomicLong bookingIdCounter = new AtomicLong(1);

    // The pricing service URL is now injected from configuration for flexibility and best practices.
    @Value("${pricing.service-url}")
    private String pricingServiceUrl;

    @Autowired
    public BookingService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /*
     * LESSON NOTE: Main Booking Creation Method
     * - Creates an booking and calculates pricing through microservice call
     * - Demonstrates the core pattern: booking creation -> pricing calculation
     */
    public BookingResponse createBooking(BookingRequest request) {
        Long bookingId = bookingIdCounter.getAndIncrement();

        // Create pricing request
        BookingPricingRequest pricingRequest = new BookingPricingRequest();
        pricingRequest.setCustomerName(request.getCustomerName());
        pricingRequest.setCity(request.getCity());
        pricingRequest.setNumberOfPeople(request.getNumberOfPeople());

        // Call pricing service to calculate total using RestTemplate
        // Best Practice: Use configuration for service URLs instead of hardcoding
        PricingResponse pricingResponse = null;
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<BookingPricingRequest> entity = new HttpEntity<>(pricingRequest, headers);
            ResponseEntity<PricingResponse> response = restTemplate.postForEntity(pricingServiceUrl, entity, PricingResponse.class);
            pricingResponse = response.getBody();
        } catch (RestClientException e) {
            // Log the error for debugging
            System.err.println("Pricing service unavailable: " + e.getMessage());
            // Continue with booking creation using fallback pricing
        }

        // Create booking response
        String status = pricingResponse != null ? "CONFIRMED" : "PENDING_PRICING";
        double totalAmount = pricingResponse != null ? pricingResponse.getTotalAmount() : 0.0;

        BookingResponse bookingResponse = new BookingResponse(
                bookingId,
                request.getCustomerName(),
                request.getCity(),
                request.getNumberOfPeople(),
                totalAmount,
                status
        );

        // Store the booking
        bookings.put(bookingId, bookingResponse);

        return bookingResponse;
    }

}