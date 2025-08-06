package com.uber.booking.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uber.booking.dto.BookingRequest;
import com.uber.booking.dto.BookingResponse;
import com.uber.booking.service.BookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookingController.class)
class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookingService bookingService;

    @Autowired
    private ObjectMapper objectMapper;

    private BookingRequest bookingRequest;
    private BookingResponse bookingResponse;

    @BeforeEach
    void setUp() {
        bookingRequest = new BookingRequest();
        bookingRequest.setCustomerName("Jane Smith");
        bookingRequest.setCity("New York, California");
        bookingRequest.setNumberOfPeople(3);

        bookingResponse = new BookingResponse(1L, "Jane Smith", "New York, California", 3, 55.75, "CONFIRMED");
    }

    @Test
    void createBooking_ShouldReturnBookingResponse_WhenValidRequestProvided() throws Exception {
        when(bookingService.createBooking(any(BookingRequest.class))).thenReturn(bookingResponse);

        mockMvc.perform(post("/api/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookingRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.bookingId").value(1))
                .andExpect(jsonPath("$.customerName").value("Jane Smith"))
                .andExpect(jsonPath("$.city").value("New York, California"))
                .andExpect(jsonPath("$.numberOfPeople").value(3))
                .andExpect(jsonPath("$.totalPrice").value(55.75))
                .andExpect(jsonPath("$.status").value("CONFIRMED"));
    }

    @Test
    void createBooking_ShouldReturnBadRequest_WhenInvalidRequestProvided() throws Exception {
        BookingRequest invalidRequest = new BookingRequest();

        mockMvc.perform(post("/api/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error").value("Validation failed"));
    }

}