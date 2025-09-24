package com.example.HotelBookingSystem.service;

import com.example.HotelBookingSystem.dto.BookingDetailRespone;
import com.example.HotelBookingSystem.model.Booking;
import com.example.HotelBookingSystem.model.Customer;
import com.example.HotelBookingSystem.model.Room;
import com.example.HotelBookingSystem.model.ManageRoom;
import com.example.HotelBookingSystem.repository.BookingRepository;
import com.example.HotelBookingSystem.repository.CustomerRepository;
import com.example.HotelBookingSystem.repository.RoomRepository;
import com.example.HotelBookingSystem.repository.ManageRoomRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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
    private RoomService roomService;

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
    public BookingDetailRespone create(
            Integer roomId,
            String customerName,
            String Phone,
            LocalDate checkIn,
            LocalDate checkOut,
            String note) {
        try {
            // Kiểm tra phòng
            Optional<Room> resuttRoom = roomService.findRoom(roomId);
            if (!resuttRoom.isPresent()) {
                throw new RuntimeException("Không có phòng phù hợp");
            }
            Room room = resuttRoom.get();

            long days = ChronoUnit.DAYS.between(checkIn, checkOut);
            if (days <= 0) {
                throw new RuntimeException("Ngày checkout phải sau ngày checkin");
            }

            // kiểm tra có trong block date không?
            List<LocalDate> blockDates = roomService.RoomBlockDay(roomId);
            if (blockDates.contains(checkIn) || blockDates.contains(checkOut)) {
                throw new RuntimeException("Ngày đã bị chặn đặt phòng");
            }

            // tổng giá
            BigDecimal totalPrice = room.getPrice()
                    .multiply(BigDecimal.valueOf(days));

            // kiểm tra hoặc tạo mớt một customer
            Optional<Customer> resultFind = customerRepository.findByPhone(Phone);
            Customer customer;
            if (resultFind.isPresent()) {
                customer = resultFind.get();
            } else {
                customer = customerService.createCustomer(customerName, Phone);
            }

            // tạo mới một booking
            Booking booking = new Booking();
            booking.setRoom(room);
            booking.setCustomer(customer);
            booking.setCheckinDate(checkIn);
            booking.setCheckoutDate(checkOut);
            booking.setTotalPrice(totalPrice);
            booking.setNote(note);

            Booking resultAddBooking =  bookingRepository.save(booking);

            BookingDetailRespone res = new BookingDetailRespone();

            res.setRoomName(resultAddBooking.getRoom().getRoomName());
            res.setCheckInDate(resultAddBooking.getCheckinDate());
            res.setCheckOutDate(resultAddBooking.getCheckoutDate());
            res.setNote(resultAddBooking.getNote());
            res.setTotalPrice(resultAddBooking.getTotalPrice());
            res.setCustomerName(resultAddBooking.getCustomer().getCustomerName());
            res.setPhone(resultAddBooking.getCustomer().getPhone());
            res.setStatus(resultAddBooking.getStatus());

            return res;
        } catch (Exception e) {
            throw new RuntimeException("Không thể tạo mới booking", e);
        }
    }
}
