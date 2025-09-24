package com.example.HotelBookingSystem.dto;

import java.time.LocalDate;

public class CreateBookingRequest {
    private Integer roomId;
    private String customerName;
    private String Phone;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private String note;
}
