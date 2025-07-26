package com.uber.booking.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/*
 * LESSON NOTE: Booking Request DTO - Client to Booking Service Communication
 *
 * This DTO represents the data that a CLIENT (like a web app or mobile app)
 * sends TO the Booking Service when creating a new Booking.
 *
 * Data Flow: CLIENT → Booking Service (via REST API)
 *
 * - Contains all information needed to create an booking
 * - Validation annotations ensure data quality before processing
 * - This is the "public API" - what external clients send to us
 */
public class BookingRequest {

    @NotBlank(message = "Customer name is required")
    private String customerName;

    @NotBlank(message = "City is required")
    private String city;

    @NotNull(message = "Number of people is required")
    @Positive(message = "Number of people must be positive")
    private Integer numberOfPeople;

    public BookingRequest() {}

    public BookingRequest(String customerName, String city, Integer numberOfPeople) {
        this.customerName = customerName;
        this.city = city;
        this.numberOfPeople = numberOfPeople;
    }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public Integer getNumberOfPeople() { return numberOfPeople; }
    public void setNumberOfPeople(Integer numberOfPeople) { this.numberOfPeople = numberOfPeople; }
}