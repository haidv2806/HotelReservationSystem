package com.example.HotelBookingSystem.repository;

import com.example.HotelBookingSystem.model.Booking;

import java.time.LocalDate;
import java.util.List;

import com.example.HotelBookingSystem.model.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
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
    //
    @Query("""
    SELECT b FROM Booking b
    JOIN b.room r
    JOIN b.customer c
    WHERE (
               :keyword IS NULL OR :keyword = ''\s
               OR LOWER(REPLACE(r.roomName, ' ', '')) LIKE LOWER(CONCAT('%', REPLACE(:keyword, ' ', ''), '%'))
               OR LOWER(REPLACE(c.customerName, ' ', '')) LIKE LOWER(CONCAT('%', REPLACE(:keyword, ' ', ''), '%'))
               OR REPLACE(c.phone, ' ', '') LIKE CONCAT('%', REPLACE(:keyword, ' ', ''), '%')
    )
            
    AND (:startDate IS NULL OR b.checkinDate >= :startDate)
    AND (:endDate IS NULL OR b.checkoutDate <= :endDate)
""")
    Page<Booking> searchBookings(
            @Param("keyword") String keyword,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            Pageable pageable);

}
