package com.example.HotelBookingSystem.service;

import com.example.HotelBookingSystem.dto.RoomDetailResponse;

import com.example.HotelBookingSystem.model.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RoomServiceImpl {
    Page<Room> findAll(Pageable pageable);

    Page<Room> searchRooms(String roomType, LocalDate checkin, LocalDate checkout, BigDecimal minPrice,
            BigDecimal maxPrice, Pageable pageable);

    RoomDetailResponse getRoomDetail(Integer room_id);

    List<LocalDate> RoomBlockDay(Integer room_id);

    Optional<Room> findRoom(Integer id);

    Room save(Room room);

    Room update(Integer id, Room room);

    void delete(Integer id);

    String deleteHandle(Integer id);
}
