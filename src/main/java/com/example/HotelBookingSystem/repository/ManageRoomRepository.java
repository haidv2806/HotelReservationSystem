package com.example.HotelBookingSystem.repository;

import com.example.HotelBookingSystem.model.ManageRoom;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ManageRoomRepository extends JpaRepository<ManageRoom, Integer> {
    
    // Lấy các record manage room theo roomId
    List<ManageRoom> findByRoom_RoomId(Integer roomId);

    //Phần của Hiếu
    // Kiểm tra overlap với ManageRoom khác (khi tạo mới)
    @Query("SELECT COUNT(m) > 0 FROM ManageRoom m " +
            "WHERE m.room.roomId = :roomId " +
            "AND (m.startDate <= :endDate AND m.endDate >= :startDate)")
    boolean existsManageRoomOverlap(@Param("roomId") Integer roomId,
                                    @Param("startDate") LocalDate startDate,
                                    @Param("endDate") LocalDate endDate);

    // Kiểm tra overlap với ManageRoom khác (khi update, bỏ qua chính nó)
    @Query("SELECT COUNT(m) > 0 FROM ManageRoom m " +
            "WHERE m.room.roomId = :roomId " +
            "AND m.manageRoomId <> :excludeId " +
            "AND (m.startDate <= :endDate AND m.endDate >= :startDate)")
    boolean existsManageRoomOverlapUpdate(@Param("roomId") Integer roomId,
                                          @Param("startDate") LocalDate startDate,
                                          @Param("endDate") LocalDate endDate,
                                          @Param("excludeId") Integer excludeId);

    // Kiểm tra overlap với Booking (PENDING, CONFIRMED, CHECKED_IN)
    @Query("SELECT COUNT(b) > 0 FROM Booking b " +
            "WHERE b.room.roomId = :roomId " +
            "AND (b.checkinDate <= :endDate AND b.checkoutDate >= :startDate) " +
            "AND b.status IN ('PENDING','CONFIRMED','CHECKED_IN')")
    boolean existsBookingOverlap(@Param("roomId") Integer roomId,
                                 @Param("startDate") LocalDate startDate,
                                 @Param("endDate") LocalDate endDate);
}
