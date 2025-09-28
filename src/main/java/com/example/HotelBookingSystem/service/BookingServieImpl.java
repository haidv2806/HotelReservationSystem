package com.example.HotelBookingSystem.service;

import com.example.HotelBookingSystem.dto.BookingDetailRespone;
import com.example.HotelBookingSystem.dto.BookingRequestDTO;
import com.example.HotelBookingSystem.model.Booking;
import com.example.HotelBookingSystem.model.Payment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BookingServieImpl {
    Optional<Booking> findBooking(Long id);

    Page<Booking> findAll(Pageable pageable);

    Booking save();

    Booking update();

    void delete();

    BookingDetailRespone create(Integer roomId, String customerName, String Phone, LocalDate checkIn, LocalDate checkOut, String note, Payment.PaymentMethod paymentMethod);
    // phan bookingconfirm Hieu
    List<Booking> getAllBookings();
    Booking getBookingById(int id);
    Booking createBooking(BookingRequestDTO dto);
    Booking confirmBooking(int bookingId, long adminId);
    Booking checkIn(int bookingId, long adminId);
    Booking checkOut(int bookingId, long adminId);
    Booking cancelBooking(int bookingId, long adminId);
}
