import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { BookingService, BookingRequest, BookingResponse } from './booking.service';

@Component({
selector: 'app-root',
standalone: true,
imports: [CommonModule, FormsModule],
templateUrl: './app.component.html',
styleUrl: './app.component.css'
})

export class AppComponent {
title = 'Uber Booking System';

bookingRequest: BookingRequest = {
customerName: '',
city: '',
numberOfPeople: 1
};

bookingResponse: BookingResponse | null = null;
errorMessage: string = '';
loading: boolean = false;

constructor(private bookingService: BookingService) {}

  async onSubmit() {
    if (this.isFormValid()) {
      this.loading = true;
      this.errorMessage = '';
      this.bookingResponse = null;

      try {
        this.bookingResponse = await this.bookingService.createBooking(this.bookingRequest);
        this.loading = false;
      } catch (error: any) {
        this.loading = false;
        if (error.error && error.error.message) {
          this.errorMessage = error.error.message;
        } else if (error.error && error.error.fieldErrors) {
          this.errorMessage = 'Please check the following fields: ' +
            Object.entries(error.error.fieldErrors)
              .map(([field, message]) => `${field}: ${message}`)
              .join(', ');
        } else {
          this.errorMessage = 'Failed to create booking. Please try again.';
        }
      }
    }
  }

  isFormValid(): boolean {
    return this.bookingRequest.customerName.trim() !== '' &&
           this.bookingRequest.city.trim() !== '' &&
           this.bookingRequest.numberOfPeople > 0;
  }

  clearForm() {
    this.bookingRequest = {
      customerName: '',
      city: '',
      numberOfPeople: 1
    };
    this.bookingResponse = null;
    this.errorMessage = '';
  }
}
