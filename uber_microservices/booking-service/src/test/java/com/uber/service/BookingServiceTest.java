package com.uber.booking.service;

import com.uber.booking.dto.BookingRequest;
import com.uber.booking.dto.BookingResponse;
import com.uber.booking.dto.BookingPricingRequest;
import com.uber.booking.dto.PricingResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private BookingService bookingService;

    private BookingRequest bookingRequest;
    private PricingResponse pricingResponse;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(bookingService, "pricingServiceUrl", "http://localhost:8081/api/pricing/calculate");

        bookingRequest = new BookingRequest();
        bookingRequest.setCustomerName("John Doe");
        bookingRequest.setCity("New York, California");
        bookingRequest.setNumberOfPeople(2);

        pricingResponse = new PricingResponse();
        pricingResponse.setTotalAmount(45.50);
    }

    @Test
    void createBooking_ShouldReturnBookingWithCalculatedPrice_WhenPricingServiceReturnsPrice() {
        ResponseEntity<PricingResponse> responseEntity = new ResponseEntity<>(pricingResponse, HttpStatus.OK);
        when(restTemplate.postForEntity(anyString(), any(HttpEntity.class), eq(PricingResponse.class)))
                .thenReturn(responseEntity);

        BookingResponse result = bookingService.createBooking(bookingRequest);

        assertNotNull(result);
        assertEquals(1L, result.getBookingId());
        assertEquals("John Doe", result.getCustomerName());
        assertEquals("New York, California", result.getCity());
        assertEquals(2, result.getNumberOfPeople());
        assertEquals(45.50, result.getTotalPrice());
        assertEquals("CONFIRMED", result.getStatus());

        verify(restTemplate, times(1)).postForEntity(anyString(), any(HttpEntity.class), eq(PricingResponse.class));
    }

    @Test
    void createBooking_ShouldReturnBookingWithZeroPrice_WhenPricingServiceThrowsException() {
        when(restTemplate.postForEntity(anyString(), any(HttpEntity.class), eq(PricingResponse.class)))
                .thenThrow(new RestClientException("Service unavailable"));

        BookingResponse result = bookingService.createBooking(bookingRequest);

        assertNotNull(result);
        assertEquals(1L, result.getBookingId());
        assertEquals("John Doe", result.getCustomerName());
        assertEquals("New York, California", result.getCity());
        assertEquals(2, result.getNumberOfPeople());
        assertEquals(0.0, result.getTotalPrice());
        assertEquals("PENDING_PRICING", result.getStatus());

        verify(restTemplate, times(1)).postForEntity(anyString(), any(HttpEntity.class), eq(PricingResponse.class));
    }

    @Test
    void createBooking_ShouldGenerateUniqueBookingIds_WhenMultipleBookingsCreated() {
        ResponseEntity<PricingResponse> responseEntity = new ResponseEntity<>(pricingResponse, HttpStatus.OK);
        when(restTemplate.postForEntity(anyString(), any(HttpEntity.class), eq(PricingResponse.class)))
                .thenReturn(responseEntity);

        BookingResponse firstBooking = bookingService.createBooking(bookingRequest);
        BookingResponse secondBooking = bookingService.createBooking(bookingRequest);

        assertNotNull(firstBooking);
        assertNotNull(secondBooking);
        assertNotEquals(firstBooking.getBookingId(), secondBooking.getBookingId());
        assertEquals(1L, firstBooking.getBookingId());
        assertEquals(2L, secondBooking.getBookingId());

        verify(restTemplate, times(2)).postForEntity(anyString(), any(HttpEntity.class), eq(PricingResponse.class));
    }
}