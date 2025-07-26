package com.uber.booking.dto;

/*
 * LESSON NOTE: Pricing Response DTO - Pricing Service to Booking Service Communication
 *
 * This DTO represents the data that the PRICING SERVICE sends BACK to the BOOKING SERVICE
 * after calculating the total cost of an booking.
 *
 * Data Flow: Pricing Service → Booking Service (via REST API response)
 *
 * - Contains the calculated total amount for the booking
 * - Used by booking Service to complete the booking creation process
 * - Simple DTO focused on just the pricing calculation result
 * - Part of the microservice communication chain
 */
public class PricingResponse {
    private Double totalAmount;

    public PricingResponse() {}

    public Double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(Double totalAmount) { this.totalAmount = totalAmount; }

}