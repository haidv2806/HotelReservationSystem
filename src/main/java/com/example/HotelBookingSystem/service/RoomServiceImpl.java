package com.example.HotelBookingSystem.service;

import com.example.HotelBookingSystem.model.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

public interface RoomServiceImpl {
    Optional<Room> findRoom(Long id);

    Page<Room> findAll(Pageable pageable);

    Room save();

    Room update();

    void delete();

    Page<Room> searchRooms(String roomType, LocalDate checkin, LocalDate checkout, BigDecimal minPrice,
            BigDecimal maxPrice, Pageable pageable);
}
