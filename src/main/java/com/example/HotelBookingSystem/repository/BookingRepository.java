package com.example.HotelBookingSystem.repository;

import com.example.HotelBookingSystem.model.Booking;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
    
    // Lấy các booking theo roomId và status
    List<Booking> findByRoom_RoomIdAndStatusIn(Integer roomId, List<Booking.Status> statuses);
}
