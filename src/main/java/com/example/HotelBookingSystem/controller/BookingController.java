package com.example.HotelBookingSystem.controller;

import com.example.HotelBookingSystem.dto.BookingRequestDTO;
import com.example.HotelBookingSystem.dto.BookingResponseDTO;
import com.example.HotelBookingSystem.dto.CreateBookingRequest;
import com.example.HotelBookingSystem.mapper.BookingConfirmMapper;
import com.example.HotelBookingSystem.model.Admin;
import com.example.HotelBookingSystem.model.Booking;
import com.example.HotelBookingSystem.service.AdminService;
import com.example.HotelBookingSystem.service.BookingService;
import com.example.HotelBookingSystem.dto.BookingDetailRespone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
                req.getNote(),
                req.getPaymentMethod());
        return ResponseEntity.ok(response);
    }

    // phan cua hieu booking confirm

    @GetMapping("/confirm")
    public List<BookingResponseDTO> getAllBookings() {
        return bookingService.getAllBookings()
                .stream()
                .map(BookingConfirmMapper::toDTO)
                .toList();
    }

    @GetMapping("/confirm/{id}")
    public BookingResponseDTO getBookingById(@PathVariable int id) {
        Booking booking = bookingService.getBookingById(id);
        return BookingConfirmMapper.toDTO(booking);
    }

    //------------------------CREATE---------------------------
    @PostMapping("/confirm/create")
    public ResponseEntity<BookingResponseDTO> createBooking(@RequestBody BookingRequestDTO dto) {
        Booking booking = bookingService.createBooking(dto);
        return ResponseEntity.ok(BookingConfirmMapper.toDTO(booking));
    }

    // ------------------- UPDATE STATUS -------------------
    @PutMapping("/confirm/update/{id}")
    public ResponseEntity<BookingResponseDTO> confirmBooking(@PathVariable("id") int bookingId,
                                                             @RequestParam Long adminId) {
        Booking booking = bookingService.confirmBooking(bookingId, adminId);
        return ResponseEntity.ok(BookingConfirmMapper.toDTO(booking));
    }

    @PutMapping("/confirm/checkin/{id}")
    public ResponseEntity<BookingResponseDTO> checkinBooking(@PathVariable("id") int bookingId,
                                                             @RequestParam Long adminId) {
        Booking booking = bookingService.checkIn(bookingId, adminId);
        return ResponseEntity.ok(BookingConfirmMapper.toDTO(booking));
    }

    @PutMapping("/confirm/checkout/{id}")
    public ResponseEntity<BookingResponseDTO> checkoutBooking(@PathVariable("id") int bookingId,
                                                              @RequestParam Long adminId) {
        Booking booking = bookingService.checkOut(bookingId, adminId);
        return ResponseEntity.ok(BookingConfirmMapper.toDTO(booking));
    }

    @PutMapping("/confirm/cancel/{id}")
    public ResponseEntity<BookingResponseDTO> cancelBooking(@PathVariable("id") int bookingId,
                                                            @RequestParam Long adminId) {
        Booking booking = bookingService.cancelBooking(bookingId, adminId);
        return ResponseEntity.ok(BookingConfirmMapper.toDTO(booking));
    }


}
