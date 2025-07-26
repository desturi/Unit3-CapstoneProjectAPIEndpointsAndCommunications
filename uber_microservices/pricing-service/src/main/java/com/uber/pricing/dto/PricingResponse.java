package com.uber.pricing.dto;

/*
 * LESSON NOTE: DTO for Pricing Response
 * - Contains detailed breakdown of price calculation
 * - Provides transparency to calling services about how price was calculated
 * - Allows front-end applications to show price breakdown to customers
 * - Demonstrates good API design with detailed response objects
 */
public class PricingResponse {

    private Double totalAmount;

    public PricingResponse() {}

    public PricingResponse(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    /*
     * LESSON NOTE: toString Method for Debugging
     * - Useful for logging and debugging
     * - Shows all price components in a readable format
     * - Good practice for DTO classes
     */
    @Override
    public String toString() {
        return String.format("PricingResponse{totalAmount=%.2f}", totalAmount);
    }
}