package com.example.HotelBookingSystem.service;

import com.example.HotelBookingSystem.dto.RoomDetailResponse;
import com.example.HotelBookingSystem.model.Booking;
import com.example.HotelBookingSystem.model.ManageRoom;
import com.example.HotelBookingSystem.model.Room;
import com.example.HotelBookingSystem.repository.BookingRepository;
import com.example.HotelBookingSystem.repository.ManageRoomRepository;
import com.example.HotelBookingSystem.repository.RoomRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.List;

@Service
public class RoomService implements RoomServiceImpl {
    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ManageRoomRepository manageRoomRepository;

    @Override
    public Optional<Room> findRoom(Integer id) {
        return roomRepository.findById(id);
    }

    @Override
    public Page<Room> findAll(Pageable pageable) {
        return roomRepository.findAll(pageable);
    }

    @Override
    public Page<Room> searchRooms(
            String roomType,
            LocalDate checkin,
            LocalDate checkout,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            Pageable pageable) {

        // Trường hợp đơn giản: lọc theo roomType + khoảng giá
        return roomRepository.searchRooms(
                roomType,
                checkin,
                checkout,
                minPrice,
                maxPrice,
                pageable);
    }

    @Override
    public List<LocalDate> RoomBlockDay(Integer roomId) {
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

        return blockDates;
    }

    @Override
    public RoomDetailResponse getRoomDetail(Integer roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found with id: " + roomId));

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

        // Tạo DTO từ entity Room
        RoomDetailResponse res = new RoomDetailResponse(
                room.getRoomName(),
                room.getDescription(),
                room.getImg(),
                room.getType(),
                room.getPrice().doubleValue(), // vì DTO dùng Double, entity dùng BigDecimal
                blockDates);

        return res;
    }

    @Override
    public Room save() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
    public Room update() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }
}