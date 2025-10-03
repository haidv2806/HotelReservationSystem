package com.example.HotelBookingSystem.mapper;

import com.example.HotelBookingSystem.dto.BookingResponseDTO;
import com.example.HotelBookingSystem.model.Booking;

//phan cua Hieu

public class BookingConfirmMapper {
    public static BookingResponseDTO toDTO(Booking booking) {
        BookingResponseDTO dto = new BookingResponseDTO();
        dto.setIdBooking(booking.getIdBooking());
        dto.setCreateAt(booking.getCreateAt());
        dto.setCheckinDate(booking.getCheckinDate());
        dto.setCheckoutDate(booking.getCheckoutDate());
        dto.setNote(booking.getNote());
        dto.setTotalPrice(booking.getTotalPrice());
        dto.setStatus(booking.getStatus().name());

        return dto;
    }
}
