package com.example.HotelBookingSystem.service;

import com.example.HotelBookingSystem.dto.BookingRequestDTO;
import com.example.HotelBookingSystem.model.Admin;
import com.example.HotelBookingSystem.model.Booking;

import java.util.List;

public interface BookingConfirmImplService {

    List<Booking> getAllBookings();
    Booking getBookingById(int id);
    Booking createBooking(BookingRequestDTO dto);
    Booking confirmBooking(int bookingId, long adminId);
    Booking checkIn(int bookingId, long adminId);
    Booking checkOut(int bookingId, long adminId);
    Booking cancelBooking(int bookingId, long adminId);

}
