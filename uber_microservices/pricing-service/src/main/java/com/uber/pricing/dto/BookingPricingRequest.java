package com.uber.pricing.dto;

public class BookingPricingRequest {
    private String customerName;
    private String city;
    private Integer numberOfPeople;

    public BookingPricingRequest() {
    }

    public BookingPricingRequest(String customerName, String city, Integer numberOfPeople) {
        this.customerName = customerName;
        this.city = city;
        this.numberOfPeople = numberOfPeople;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(Integer numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }
}
