package com.example.HotelBookingSystem.repository;

import com.example.HotelBookingSystem.model.ManageRoom;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManageRoomRepository extends JpaRepository<ManageRoom, Integer> {
    
    // Lấy các record manage room theo roomId
    List<ManageRoom> findByRoom_RoomId(Integer roomId);
}
