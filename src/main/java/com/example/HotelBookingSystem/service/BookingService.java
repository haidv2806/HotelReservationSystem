package com.example.HotelBookingSystem.service;

import com.example.HotelBookingSystem.model.Booking;
import com.example.HotelBookingSystem.repository.BookingRepository;
import com.example.HotelBookingSystem.repository.CustomerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookingService implements BookingServieImpl {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired CustomerRepository customerRepository;

    @Override
    public Optional<Booking> findBooking(Long id) {
        return Optional.empty();
    }

    @Override
    public Page<Booking> findAll(Pageable pageable) {
        return bookingRepository.findAll(pageable);
    }

    @Override
    public Booking save() {
        return null;
    }

    @Override
    public Booking update() {
        return null;
    }

    @Override
    public void delete() {

    }

}
