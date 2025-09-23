package com.example.HotelBookingSystem.service;

import com.example.HotelBookingSystem.model.Room;
import com.example.HotelBookingSystem.repository.RoomRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class RoomService implements RoomServiceImpl {
    @Autowired
    private RoomRepository roomRepository;

    @Override
    public Optional<Room> findRoom(Long id) {
        return Optional.empty();
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
