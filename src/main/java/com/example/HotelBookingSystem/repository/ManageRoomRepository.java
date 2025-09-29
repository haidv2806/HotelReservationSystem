package com.example.HotelBookingSystem.repository;

import com.example.HotelBookingSystem.model.ManageRoom;
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
    @Query("SELECT COUNT(m) > 0 FROM ManageRoom m " +
            "WHERE m.room.roomId = :roomId " +
            "AND (m.startDate <= :endDate AND m.endDate >= :startDate)")
    boolean existsOverlappingCreate(@Param("roomId") Integer roomId,
                                    @Param("startDate") java.time.LocalDate startDate,
                                    @Param("endDate") java.time.LocalDate endDate);


    // Kiểm tra overlap khi update (bỏ qua chính nó)
    @Query("SELECT COUNT(m) > 0 FROM ManageRoom m " +
            "WHERE m.room.roomId = :roomId " +
            "AND m.manageRoomId <> :excludeId " +
            "AND (m.startDate <= :endDate AND m.endDate >= :startDate)")
    boolean existsOverlappingUpdate(@Param("roomId") Integer roomId,
                                    @Param("startDate") java.time.LocalDate startDate,
                                    @Param("endDate") java.time.LocalDate endDate,
                                    @Param("excludeId") Integer excludeId);
}
