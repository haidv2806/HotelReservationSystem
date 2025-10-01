package com.example.HotelBookingSystem.repository;

import com.example.HotelBookingSystem.model.Booking;

import java.time.LocalDate;
import java.util.List;

import com.example.HotelBookingSystem.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
    
    // Lấy các booking theo roomId và status
    List<Booking> findByRoom_RoomIdAndStatusIn(Integer roomId, List<Booking.Status> statuses);
    // Repo bookingconfirm cua Hieu
    @Query("SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END " +
            "FROM Booking b " +
            "WHERE b.room = :room " +
            "AND b.status <> 'CANCELLED' " +
            "AND (b.checkinDate <= :checkoutDate AND b.checkoutDate >= :checkinDate)")
    boolean existsByRoomAndDateRange(@Param("room") Room room,
                                     @Param("checkinDate") LocalDate checkinDate,
                                     @Param("checkoutDate") LocalDate checkoutDate);
}
