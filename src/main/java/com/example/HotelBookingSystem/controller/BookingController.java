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

    @GetMapping("/booking")
    public List<BookingResponseDTO> getAllBookings() {
        return bookingService.getAllBookings()
                .stream()
                .map(BookingConfirmMapper::toDTO)
                .toList();
    }

    @GetMapping("/booking/{id}")
    public BookingResponseDTO getBookingById(@PathVariable int id) {
        Booking booking = bookingService.getBookingById(id);
        return BookingConfirmMapper.toDTO(booking);
    }
    //--------------- POST----------------------------
    @PostMapping("/create")
    public Page<BookingResponseDTO> getAll(@RequestBody BookingResponseDTO dto){
        return bookingService.getAllBookingsPaginated(dto.getCheckinDate(),dto.getCheckoutDate(),dto.getRoomName(), PageRequest.of(0, 10));
    }

    // ------------------- UPDATE STATUS -------------------
    @PutMapping("/booking/{id}")
    public ResponseEntity<BookingResponseDTO> updateStatus(@PathVariable("id") int bookingId,
                                                           @RequestBody BookingRequestDTO requestDTO) {
        BookingResponseDTO response = bookingService.updateBookingStatus(bookingId, requestDTO);
        return ResponseEntity.ok(response);
    }

    //----------------DELETE------------------
    @DeleteMapping("/booking/{id}")
    public ResponseEntity<?> deleteBooking(@PathVariable("id") Integer id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.ok().build();
    }

}
