package com.example.HotelBookingSystem.dto;

import java.util.List;
public record ChatRequest(
    String message,
    List<String> history
) {}
