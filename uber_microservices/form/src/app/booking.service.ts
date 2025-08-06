import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { firstValueFrom } from 'rxjs';

export interface BookingRequest {
customerName: string;
city: string;
numberOfPeople: number;
}

export interface BookingResponse {
bookingId: number;
customerName: string;
city: string;
numberOfPeople: number;
totalPrice: number;
status: string;
}

@Injectable({
providedIn: 'root'
})

export class BookingService {
private apiUrl = 'http://localhost:8080/api/bookings';

constructor(private http: HttpClient) { }

  async createBooking(bookingRequest: BookingRequest): Promise<BookingResponse> {
    return firstValueFrom(this.http.post<BookingResponse>(this.apiUrl, bookingRequest));
  }
}
