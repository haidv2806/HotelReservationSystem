package com.example.HotelBookingSystem.controller;

import com.example.HotelBookingSystem.dto.CreateBookingRequest;
import com.example.HotelBookingSystem.model.Admin;
import com.example.HotelBookingSystem.service.AdminService;
import com.example.HotelBookingSystem.service.BookingService;
import com.example.HotelBookingSystem.dto.BookingDetailRespone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @PostMapping("/booking")
    public ResponseEntity<BookingDetailRespone> createBooking(@RequestBody CreateBookingRequest req) {
        BookingDetailRespone response = bookingService.create(
                req.getRoomId(),
                req.getCustomerName(),
                req.getPhone(),
                req.getCheckIn(),
                req.getCheckOut(),
                req.getNote());
        return ResponseEntity.ok(response);
    }

}
