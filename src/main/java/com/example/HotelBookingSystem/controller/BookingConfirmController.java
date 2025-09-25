package com.example.HotelBookingSystem.controller;

import com.example.HotelBookingSystem.dto.BookingRequestDTO;
import com.example.HotelBookingSystem.dto.BookingResponseDTO;
import com.example.HotelBookingSystem.mapper.BookingConfirmMapper;
import com.example.HotelBookingSystem.model.Booking;
import com.example.HotelBookingSystem.service.BookingConfirmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/confirm")
public class BookingConfirmController {
    @Autowired
    private BookingConfirmService bookingConfirmService;

    @GetMapping
    public List<BookingResponseDTO> getAllBookings() {
        return bookingConfirmService.getAllBookings()
                .stream()
                .map(BookingConfirmMapper::toDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public BookingResponseDTO getBookingById(@PathVariable int id) {
        Booking booking = bookingConfirmService.getBookingById(id);
        return BookingConfirmMapper.toDTO(booking);
    }

    //------------------------CREATE---------------------------
    @PostMapping
    public ResponseEntity<BookingResponseDTO> createBooking(@RequestBody BookingRequestDTO dto) {
        Booking booking = bookingConfirmService.createBooking(dto);
        return ResponseEntity.ok(BookingConfirmMapper.toDTO(booking));
    }

    // ------------------- UPDATE STATUS -------------------
    @PutMapping("/update/{id}")
    public ResponseEntity<BookingResponseDTO> confirmBooking(@PathVariable("id") int bookingId,
                                                  @RequestParam Long adminId) {
        Booking booking = bookingConfirmService.confirmBooking(bookingId, adminId);
        return ResponseEntity.ok(BookingConfirmMapper.toDTO(booking));
    }

    @PutMapping("/checkin/{id}")
    public ResponseEntity<BookingResponseDTO> checkinBooking(@PathVariable("id") int bookingId,
                                                  @RequestParam Long adminId) {
        Booking booking = bookingConfirmService.checkIn(bookingId, adminId);
        return ResponseEntity.ok(BookingConfirmMapper.toDTO(booking));
    }

    @PutMapping("/checkout/{id}")
    public ResponseEntity<BookingResponseDTO> checkoutBooking(@PathVariable("id") int bookingId,
                                                   @RequestParam Long adminId) {
        Booking booking = bookingConfirmService.checkOut(bookingId, adminId);
        return ResponseEntity.ok(BookingConfirmMapper.toDTO(booking));
    }

    @PutMapping("/cancel/{id}")
    public ResponseEntity<BookingResponseDTO> cancelBooking(@PathVariable("id") int bookingId,
                                                 @RequestParam Long adminId) {
        Booking booking = bookingConfirmService.cancelBooking(bookingId, adminId);
        return ResponseEntity.ok(BookingConfirmMapper.toDTO(booking));
    }
}
