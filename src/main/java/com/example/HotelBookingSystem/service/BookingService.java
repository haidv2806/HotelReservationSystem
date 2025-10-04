package com.example.HotelBookingSystem.service;

import com.example.HotelBookingSystem.dto.BookingDetailRespone;
import com.example.HotelBookingSystem.dto.BookingRequestDTO;
import com.example.HotelBookingSystem.dto.BookingResponseDTO;
import com.example.HotelBookingSystem.mapper.BookingConfirmMapper;
import com.example.HotelBookingSystem.model.*;
import com.example.HotelBookingSystem.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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
    private PaymentRepository paymentRepository;

    @Autowired
    private QRPaymentService qrPaymentService;

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
    //add them
    @Autowired
    private  AdminRepository adminRepository;

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
            String note,
            Payment.PaymentMethod paymentMethod) {
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
            LocalDate d = checkIn;
            while (d.isBefore(checkOut)) {
                if (blockDates.contains(d)) {
                    throw new RuntimeException("Ngày đã bị chặn đặt phòng: " + d);
                }
                d = d.plusDays(1);
            }

            // tổng giá
            BigDecimal totalPrice = room.getPrice()
                    .multiply(BigDecimal.valueOf(days));

            // kiểm tra hoặc tạo mớt một customer
            Optional<Customer> resultFind = customerRepository.findByPhone(Phone);
            Customer customer;
            if (resultFind.isPresent()) {
                if (resultFind.get().getStatus() == Customer.Status.blacklist) {
                    throw new RuntimeException("Khách hàng bị hạn chế đặt phòng");
                }
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

            Booking resultAddBooking = bookingRepository.save(booking);

            // thêm phương thức thanh toán
            Payment addPayment = new Payment();
            addPayment.setBooking(resultAddBooking);
            addPayment.setAmount(resultAddBooking.getTotalPrice());
            addPayment.setPaymentMethod(paymentMethod);

            Payment resultPayment = paymentRepository.save(addPayment);

            // tạo QR code
            String description = resultAddBooking.getCustomer().getCustomerName()
                    + " | SĐT: " + resultAddBooking.getCustomer().getPhone()
                    + " | Nhận: " + resultAddBooking.getCheckinDate()
                    + " | Trả: " + resultAddBooking.getCheckoutDate()
                    + " | Phòng: " + resultAddBooking.getRoom().getRoomName();

            String qrPath = qrPaymentService.generateQR(totalPrice, description);

            // nội dung trả về
            BookingDetailRespone res = new BookingDetailRespone();

            res.setRoomName(resultAddBooking.getRoom().getRoomName());
            res.setCheckInDate(resultAddBooking.getCheckinDate());
            res.setCheckOutDate(resultAddBooking.getCheckoutDate());
            res.setNote(resultAddBooking.getNote());
            res.setTotalPrice(resultAddBooking.getTotalPrice());
            res.setCustomerName(resultAddBooking.getCustomer().getCustomerName());
            res.setPhone(resultAddBooking.getCustomer().getPhone());
            res.setStatus(resultAddBooking.getStatus());
            res.setPaymentMethod(resultPayment.getPaymentMethod());
            res.setQRCode(qrPath);

            return res;
        } catch (Exception e) {
            throw new RuntimeException("Không thể tạo mới booking", e);
        }
    }
    //phan confirmbooking cua Hieu
    @Override
    public List<Booking>getAllBookings(){
        return bookingRepository.findAll();
    }
    @Override
    public Page<BookingResponseDTO>getAllBookingsPaginated(LocalDate start, LocalDate end,
                                                String search,
                                                Pageable pageable){
        Page<Booking> pageResult = bookingRepository.searchBookings(search, start, end, pageable);

        return pageResult.map(booking -> {
            BookingResponseDTO dto = new BookingResponseDTO();

            dto.setIdBooking(booking.getIdBooking());
            dto.setRoomName(booking.getRoom() != null ? booking.getRoom().getRoomName() : null);
            dto.setCustomerName(booking.getCustomer() != null ? booking.getCustomer().getCustomerName() : null);
            dto.setCheckinDate(booking.getCheckinDate());
            dto.setCheckoutDate(booking.getCheckoutDate());
            dto.setStatus(booking.getStatus().name());
            dto.setNote(booking.getNote());
            dto.setTotalPrice(booking.getTotalPrice());
            dto.setCreateAt(booking.getCreateAt());
            return dto;
        });
    }
    @Override
    public Booking getBookingById(int id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking không tồn tại với id = " + id));
    }

    @Override
    public BookingResponseDTO updateBookingStatus(int bookingId, BookingRequestDTO requestDTO) {
        // 1. Tìm booking
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found with ID: " + bookingId));

        if (requestDTO.getStatus() != null) {
            // 1. Nếu DTO là enum:
            booking.setStatus(requestDTO.getStatus());
            // 2. Nếu DTO là String, dùng:
            // booking.setStatus(Booking.Status.valueOf(requestDTO.getStatus()));
        }

        if (requestDTO.getNote() != null) {
            booking.setNote(requestDTO.getNote());
        }

        Booking updated = bookingRepository.save(booking);
        return BookingConfirmMapper.toDTO(updated);
    }

    public void deleteBooking(Integer id) {
        if (!bookingRepository.existsById(id)) {
            throw new RuntimeException("Booking không tồn tại!");
        }
        bookingRepository.deleteById(id);
    }

}