package com.uber.booking.dto;

/*
 * LESSON NOTE: Booking Pricing Request DTO - Booking Service to Pricing Service Communication
 *
 * This DTO represents the data that the BOOKING SERVICE sends TO the PRICING SERVICE
 * when it needs to calculate the total cost of an booking.
 *
 * Data Flow: Booking Service → Pricing Service (via REST API)
 *
 * Key Differences from BookingRequest:
 * - BookingRequest: What CLIENTS send to us (external API)
 * - BookingPricingRequest: What WE send to other services (internal API)
 *
 * Why separate DTOs?
 * - Different services may need different data formats
 * - Internal APIs can change without affecting external clients
 * - Security: We control what data gets sent between services
 * - The pricing service might need additional fields in the future
 */

public class BookingPricingRequest {
    private String customerName;
    private String city;
    private Integer numberOfPeople;

    public BookingPricingRequest() {}

    public BookingPricingRequest(String customerName, String city, Integer numberOfPeople) {
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