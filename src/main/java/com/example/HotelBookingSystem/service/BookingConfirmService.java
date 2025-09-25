package com.example.HotelBookingSystem.service;

import com.example.HotelBookingSystem.dto.BookingRequestDTO;
import com.example.HotelBookingSystem.dto.BookingResponseDTO;
import com.example.HotelBookingSystem.model.Admin;
import com.example.HotelBookingSystem.model.Booking;
import com.example.HotelBookingSystem.model.Customer;
import com.example.HotelBookingSystem.model.Room;
import com.example.HotelBookingSystem.repository.AdminRepository;
import com.example.HotelBookingSystem.repository.BookingConfirmRepository;
import com.example.HotelBookingSystem.repository.CustomerRepository;
//import com.example.HotelBookingSystem.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingConfirmService implements BookingConfirmImplService{
    @Autowired
    private BookingConfirmRepository bookingConfirmRepository;
    @Autowired
    private AdminRepository adminRepository;
//    @Autowired
//    private RoomRepository roomRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public List<Booking>getAllBookings(){
        return bookingConfirmRepository.findAll();
    }
    @Override
    public Booking getBookingById(int id) {
        return bookingConfirmRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking không tồn tại với id = " + id));
    }
    @Override
    public Booking createBooking(BookingRequestDTO bookingRequestDTO) {
        // 1. Kiểm tra Customer
        Customer customer = customerRepository.findById(bookingRequestDTO.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer không tồn tại với id = " + bookingRequestDTO.getCustomerId()));

        // 2. Kiểm tra Room
//       Room room = roomRepository.findById(bookingRequestDTO.getRoomId())
//                .orElseThrow(() -> new RuntimeException("Room không tồn tại với id = " + bookingRequestDTO.getRoomId()));

        // 3. Validate ngày
        if (!bookingRequestDTO.getCheckinDate().isBefore(bookingRequestDTO.getCheckoutDate())) {
            throw new RuntimeException("Ngày checkin phải nhỏ hơn checkout");
        }

//        // 4. Kiểm tra trùng phòng cùng thời gian
//        boolean overlap = bookingConfirmRepository.existsByRoomAndDateRange(
//                room,
//                bookingRequestDTO.getCheckinDate(),
//                bookingRequestDTO.getCheckoutDate()
//        );
//        if (overlap) {
//            throw new RuntimeException("Phòng này đã được đặt trong khoảng thời gian đó");
//        }

        // 5. Tạo booking mới
        Booking booking = new Booking();
        booking.setCustomer(customer);
        booking.setCheckinDate(bookingRequestDTO.getCheckinDate());
        booking.setCheckoutDate(bookingRequestDTO.getCheckoutDate());
        booking.setNote(bookingRequestDTO.getNote());
        booking.setTotalPrice(bookingRequestDTO.getTotalPrice());
        booking.setStatus(Booking.Status.PENDING);

        return bookingConfirmRepository.save(booking);
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
        return bookingConfirmRepository.save(booking);
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
        return bookingConfirmRepository.save(booking);
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
        return bookingConfirmRepository.save(booking);
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
        return bookingConfirmRepository.save(booking);
    }


}
