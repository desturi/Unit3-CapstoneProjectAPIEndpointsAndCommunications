package com.uber.booking.dto;

/*
 * LESSON NOTE: Booking Response DTO - Booking Service to Client Communication
 *
 * This DTO represents the data that the BOOKING SERVICE sends BACK to the CLIENT
 * after successfully creating an booking.
 *
 * Data Flow: Booking Service → CLIENT (via REST API response)
 *
 * - Contains booking confirmation details with generated booking ID
 * - Includes calculated total from pricing service
 * - Shows booking status (CONFIRMED, PENDING_PRICING, etc.)
 * - This is what clients receive as confirmation of their booking
 * - Similar to cab booking confirmation structure in capstone projects
 */
public class BookingResponse {

    private Long bookingId;
    private String customerName;
    private String city;
    private Integer numberOfPeople;
    private double totalPrice;
    private String status;

    public BookingResponse() {}

    public BookingResponse(Long bookingId, String customerName, String city,
                         Integer numberOfPeople, double totalPrice, String status) {
        this.bookingId = bookingId;
        this.customerName = customerName;
        this.city = city;
        this.numberOfPeople = numberOfPeople;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    public Long getBookingId() { return bookingId; }
    public void setBookingId(Long bookingId) { this.bookingId = bookingId; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public Integer getNumberOfPeople() { return numberOfPeople; }
    public void setNumberOfPeople(Integer numberOfPeople) { this.numberOfPeople = numberOfPeople; }

    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}