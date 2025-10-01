package com.example.HotelBookingSystem.service;

import com.example.HotelBookingSystem.dto.BookingDetailRespone;
import com.example.HotelBookingSystem.model.Booking;
import com.example.HotelBookingSystem.model.Payment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Optional;

public interface BookingServieImpl {
    Optional<Booking> findBooking(Long id);

    Page<Booking> findAll(Pageable pageable);

    Booking save();

    Booking update();

    void delete();

    BookingDetailRespone create(Integer roomId, String customerName, String Phone, LocalDate checkIn, LocalDate checkOut, String note, Payment.PaymentMethod paymentMethod);
}
