package com.example.HotelBookingSystem.repository;

import com.example.HotelBookingSystem.dto.RoomDetailResponse;
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

    @Query("""
                SELECT r
                FROM Room r
                WHERE (:type IS NULL OR r.type = :type)
                  AND (:minPrice IS NULL OR r.price >= :minPrice)
                  AND (:maxPrice IS NULL OR r.price <= :maxPrice)
                  AND r.status = 'available'
                  AND r.roomId NOT IN (
                      SELECT b.room.roomId
                      FROM Booking b
                      WHERE b.status IN ('PENDING','CONFIRMED','CHECKED_IN')
                        AND (:checkInDate < b.checkoutDate AND :checkOutDate > b.checkinDate)
                  )
                  AND r.roomId NOT IN (
                      SELECT mr.room.roomId
                      FROM ManageRoom mr
                      WHERE (:checkInDate < mr.endDate AND :checkOutDate > mr.startDate)
                  )
            """)
    Page<Room> searchRooms(
            @Param("type") String type,
            @Param("checkInDate") LocalDate checkInDate,
            @Param("checkOutDate") LocalDate checkOutDate,
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice,
            Pageable pageable);

            
    @Query("""
                SELECT new com.example.HotelBookingSystem.dto.RoomDetailResponse(
                    r.roomName,
                    r.description,
                    r.img,
                    r.type,
                    r.price,
                    null
                )
                FROM Room r
                WHERE r.roomId = :roomId
            """)
    RoomDetailResponse findRoomDetail(@Param("roomId") Integer roomId);

}
