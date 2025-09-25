package com.example.HotelBookingSystem.repository;

import com.example.HotelBookingSystem.model.Booking;
import com.example.HotelBookingSystem.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface BookingConfirmRepository extends JpaRepository<Booking, Integer> {
    @Query("SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END " +
            "FROM Booking b " +
            "WHERE b.room = :room " +
            "AND b.status <> 'CANCELLED' " +
            "AND (b.checkinDate <= :checkoutDate AND b.checkoutDate >= :checkinDate)")
    boolean existsByRoomAndDateRange(@Param("room") Room room,
                                     @Param("checkinDate") LocalDate checkinDate,
                                     @Param("checkoutDate") LocalDate checkoutDate);
}

