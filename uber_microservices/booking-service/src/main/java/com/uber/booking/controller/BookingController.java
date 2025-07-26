package com.uber.booking.controller;

import com.uber.booking.dto.BookingRequest;
import com.uber.booking.dto.BookingResponse;
import com.uber.booking.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.validation.FieldError;
import java.util.Map;
import java.util.HashMap;

/*
 * LESSON NOTE: Simplified booking Service REST Controller
 * - Focuses on creating booking (like "book a cab")
 * - Demonstrates microservice communication with pricing service
 * - Simplified from complex CRUD to core functionality
 */
@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    // Constructor-based dependency injection: Spring injects BookingService here
    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    /*
     * LESSON NOTE: Main Booking Creation Endpoint
     * - @PostMapping: Maps HTTP POST requests
     * - @RequestBody: Accepts booking request data
     * - Demonstrates microservice communication with pricing service
     * - Similar to "book a cab" functionality in capstone
     */
    @PostMapping
    public ResponseEntity<BookingResponse> createBooking(@Valid @RequestBody BookingRequest request) {
        BookingResponse response = bookingService.createBooking(request);
        return ResponseEntity.ok(response);
    }

    /*
     * LESSON NOTE: Health Check Endpoint
     * - Simple endpoint to verify service is running
     * - Important for microservices deployment and orchestration
     */
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Booking Service is running!");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(MethodArgumentNotValidException e) {
        Map<String, Object> errorResponse = new HashMap<>();
        Map<String, String> fieldErrors = new HashMap<>();

        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            fieldErrors.put(error.getField(), error.getDefaultMessage());
        }

        errorResponse.put("error", "Validation failed");
        errorResponse.put("message", "Please check the required fields");
        errorResponse.put("fieldErrors", fieldErrors);
        errorResponse.put("timestamp", java.time.Instant.now().toString());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleException(Exception e) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("error", "Booking processing failed");
        errorResponse.put("message", "Unable to process booking at this time. Please try again later.");
        errorResponse.put("timestamp", java.time.Instant.now().toString());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(errorResponse);
    }

}