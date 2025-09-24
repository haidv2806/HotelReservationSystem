package com.example.HotelBookingSystem.service;

import com.example.HotelBookingSystem.model.Booking;
import com.example.HotelBookingSystem.model.Customer;
import com.example.HotelBookingSystem.model.ManageRoom;
import com.example.HotelBookingSystem.repository.BookingRepository;
import com.example.HotelBookingSystem.repository.CustomerRepository;
import com.example.HotelBookingSystem.repository.RoomRepository;
import com.example.HotelBookingSystem.repository.ManageRoomRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookingService implements BookingServieImpl {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ManageRoomRepository manageRoomRepository;

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

    @Override
    public Booking create(
            Integer roomId,
            String customerName,
            String Phone,
            LocalDate checkIn,
            LocalDate checkOut,
            String note) {
        // Lấy danh sách ngày bị block từ booking + manageRoom
        List<LocalDate> blockDates = new ArrayList<>();

        // Block từ Booking
        List<Booking> bookings = bookingRepository.findByRoom_RoomIdAndStatusIn(
                roomId,
                List.of(Booking.Status.PENDING, Booking.Status.CONFIRMED, Booking.Status.CHECKED_IN));
        bookings.forEach(b -> {
            LocalDate d = b.getCheckinDate();
            while (!d.isAfter(b.getCheckoutDate().minusDays(1))) {
                blockDates.add(d);
                d = d.plusDays(1);
            }
        });

        // Block từ ManageRoom
        List<ManageRoom> manages = manageRoomRepository.findByRoom_RoomId(roomId);
        manages.forEach(m -> {
            LocalDate d = m.getStartDate();
            while (!d.isAfter(m.getEndDate())) {
                blockDates.add(d);
                d = d.plusDays(1);
            }
        });

        // kiểm tra có trong block date không?

        // kiểm tra hoặc tạo mớt một customer
        Optional<Customer> resultFind = customerRepository.findByPhone(Phone);
        Customer customer;
        if (customer.isPresent()) {
            customer = resultFind.get();
        } else {
            customer = customerService.createCustomer(customerName, Phone);
        }

        // khai báo một vài trường

        // tạo mới một booking
        Booking booking = new Booking();
        booking.setRoom(null);

    }
}
