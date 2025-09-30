package com.example.HotelBookingSystem.dto;

import com.example.HotelBookingSystem.model.Booking;

import java.math.BigDecimal;
import java.time.LocalDate;

//phan cua Hieu

public class BookingRequestDTO {
    private Booking.Status status;

    private String note;



    // getters & setters


    public Booking.Status getStatus() {
        return status;
    }

    public void setStatus(Booking.Status status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
