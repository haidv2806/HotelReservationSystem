package com.example.HotelBookingSystem.service;

import com.example.HotelBookingSystem.model.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface BookingServieImpl {
    Optional<Booking> findBooking(Long id);

    Page<Booking> findAll(Pageable pageable);

    Booking save();

    Booking update();

    void delete();

}
