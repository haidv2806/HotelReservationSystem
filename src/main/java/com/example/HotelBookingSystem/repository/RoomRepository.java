package com.example.HotelBookingSystem.repository;

import com.example.HotelBookingSystem.model.Room;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {
    Page<Room> findAll(Pageable pageable);

    // @Query("SELECT * FROM Room " +
    //         "JOIN Manage_room ON Room.room_id = Manage_room.room_id" +
    //         "JOIN Booking ON Room.room_id = Booking.room_id" +
    //         "WHERE (:type IS NULL OR Room.type = :type) " +
    //         "AND (:minPrice IS NULL OR Room.price >= :minPrice) " +
    //         "AND (:maxPrice IS NULL OR Room.price <= :maxPrice)")
    // Page<Room> searchRooms(
    //         @Param("type") String type,
    //         @Param("checkin") LocalDate checkin,
    //         @Param("checkout") LocalDate checkout,
    //         @Param("minPrice") BigDecimal minPrice,
    //         @Param("maxPrice") BigDecimal maxPrice,
    //         Pageable pageable);

}
