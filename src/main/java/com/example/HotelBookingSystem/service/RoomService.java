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
                Room.Status.available,
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

        // Tạo dto từ entity Room
        RoomDetailResponse res = new RoomDetailResponse(
                room.getRoomName(),
                room.getDescription(),
                room.getImg(),
                room.getType(),
                room.getPrice().doubleValue(), // vì dto dùng Double, entity dùng BigDecimal
                blockDates);

        return res;
    }


    public Room save(Room room) {
        return roomRepository.save(room);
    }

    @Override
    public Room update(Integer id, Room newRoom) {
        return roomRepository.findById(id)
                .map(room -> {
                    room.setRoomName(newRoom.getRoomName());
                    room.setType(newRoom.getType());
                    room.setPrice(newRoom.getPrice());
                    room.setDescription(newRoom.getDescription());
                    room.setImg(newRoom.getImg());
                    room.setStatus(newRoom.getStatus());
                    return roomRepository.save(room);
                })
                .orElse(null);
    }

    @Override
    public void delete(Integer id) {
        roomRepository.findById(id).ifPresent(room -> {
            room.setStatus(Room.Status.deleted);
            roomRepository.save(room);
        });
    }


    @Override
    public String deleteHandle(Integer id) {
        Optional<Room> result = this.findRoom(id);

        if (result.isPresent()) {
            Room room = result.get();
            if (room.getStatus() == Room.Status.deleted) {
                return "Phòng đã bị xóa trước đó";
            }
            this.delete(id); // gọi hàm set status = deleted
            return "Xóa thành công";
        } else {
            return "Không tồn tại phòng";
        }
    }
}

