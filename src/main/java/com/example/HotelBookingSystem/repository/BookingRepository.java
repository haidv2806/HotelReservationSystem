package com.example.HotelBookingSystem.repository;

import com.example.HotelBookingSystem.model.Booking;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
            Pageable pageable
    );

    // Doanh thu theo tháng (booking CONFIRMED)
    @Query(value = """
        SELECT DATE_FORMAT(b.checkin_date, '%Y-%m') AS month, 
               SUM(b.total_price) AS revenue
        FROM booking b
        WHERE b.status = 'CONFIRMED'
        GROUP BY DATE_FORMAT(b.checkin_date, '%Y-%m')
        ORDER BY month ASC
        """, nativeQuery = true)
    List<Object[]> getRevenueByMonth();

    // Tổng doanh thu (booking CONFIRMED) → khớp với biểu đồ
    @Query("SELECT COALESCE(SUM(b.totalPrice), 0) FROM Booking b WHERE b.status = 'CONFIRMED'")
    BigDecimal getTotalRevenueConfirmed();

    // Thống kê trạng thái booking
    @Query("SELECT b.status, COUNT(b) FROM Booking b GROUP BY b.status")
    List<Object[]> getBookingStatusStats();

    // Loại phòng được đặt nhiều nhất (CONFIRMED, CHECKED_OUT)
    @Query("SELECT r.type, COUNT(b) FROM Booking b JOIN b.room r WHERE b.status IN ('CONFIRMED', 'CHECKED_OUT') GROUP BY r.type ORDER BY COUNT(b) DESC")
    List<Object[]> getTopRoomTypesBooked();
}


