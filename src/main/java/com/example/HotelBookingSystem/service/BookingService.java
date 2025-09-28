package com.example.HotelBookingSystem.service;

import com.example.HotelBookingSystem.dto.BookingDetailRespone;
import com.example.HotelBookingSystem.dto.BookingRequestDTO;
import com.example.HotelBookingSystem.model.*;
import com.example.HotelBookingSystem.repository.*;

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
    public Booking getBookingById(int id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking không tồn tại với id = " + id));
    }
    @Override
    public Booking createBooking(BookingRequestDTO bookingRequestDTO) {
        // 1. Kiểm tra Customer
        Customer customer = customerRepository.findById(bookingRequestDTO.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer không tồn tại với id = " + bookingRequestDTO.getCustomerId()));

        // 2. Kiểm tra Room
       Room room = roomRepository.findById(bookingRequestDTO.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room không tồn tại với id = " + bookingRequestDTO.getRoomId()));

        // 3. Validate ngày
        if (!bookingRequestDTO.getCheckinDate().isBefore(bookingRequestDTO.getCheckoutDate())) {
            throw new RuntimeException("Ngày checkin phải nhỏ hơn checkout");
        }

        // 4. Kiểm tra trùng phòng cùng thời gian
        boolean overlap = bookingRepository.existsByRoomAndDateRange(
                room,
                bookingRequestDTO.getCheckinDate(),
                bookingRequestDTO.getCheckoutDate()
        );
        if (overlap) {
            throw new RuntimeException("Phòng này đã được đặt trong khoảng thời gian đó");
        }

        // 5. Tạo booking mới
        Booking booking = new Booking();
        booking.setCustomer(customer);
        booking.setCheckinDate(bookingRequestDTO.getCheckinDate());
        booking.setCheckoutDate(bookingRequestDTO.getCheckoutDate());
        booking.setNote(bookingRequestDTO.getNote());
        booking.setTotalPrice(bookingRequestDTO.getTotalPrice());
        booking.setStatus(Booking.Status.PENDING);

        return bookingRepository.save(booking);
    }

    @Override
    public Booking confirmBooking(int bookingId, long adminId) {
        Booking booking = getBookingById(bookingId);
        if(booking.getStatus() != Booking.Status.PENDING){
            throw new RuntimeException("Booking must be PENDING to confirm");
        }
        booking.setStatus(Booking.Status.CONFIRMED);
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new RuntimeException("Admin không tồn tại với id = " + adminId));

        // Cập nhật trạng thái và admin xác nhận
        booking.setStatus(Booking.Status.CONFIRMED);
        booking.setAdmin(admin);
        return bookingRepository.save(booking);
    }

    @Override
    public Booking checkIn(int bookingId, long adminId) {
        Booking booking = getBookingById(bookingId);
        if (booking.getStatus() != Booking.Status.CONFIRMED) {
            throw new RuntimeException("Booking must be CONFIRMED to check in");
        }
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new RuntimeException("Admin không tồn tại với id = " + adminId));

        // Cập nhật trạng thái và admin xác nhận
        booking.setAdmin(admin);
        booking.setStatus(Booking.Status.CHECKED_IN);
        return bookingRepository.save(booking);
    }

    @Override
    public Booking checkOut(int bookingId, long adminId) {
        Booking booking = getBookingById(bookingId);
        if (booking.getStatus() != Booking.Status.CHECKED_IN) {
            throw new RuntimeException("Booking must be CHECKED_IN to check out");
        }
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new RuntimeException("Admin không tồn tại với id = " + adminId));

        // Cập nhật trạng thái và admin xác nhận
        booking.setAdmin(admin);
        booking.setStatus(Booking.Status.CHECKED_OUT);
        return bookingRepository.save(booking);
    }

    @Override
    public Booking cancelBooking(int bookingId, long adminId) {
        Booking booking = getBookingById(bookingId);
        if (booking.getStatus() == Booking.Status.CHECKED_OUT) {
            throw new RuntimeException("Cannot cancel a booking that is already checked out");
        }
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new RuntimeException("Admin không tồn tại với id = " + adminId));

        // Cập nhật trạng thái và admin xác nhận
        booking.setAdmin(admin);
        booking.setStatus(Booking.Status.CANCELLED);
        return bookingRepository.save(booking);
    }

}
