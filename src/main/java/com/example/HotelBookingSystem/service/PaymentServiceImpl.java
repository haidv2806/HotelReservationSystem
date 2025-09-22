package com.example.HotelBookingSystem.service;

import com.example.HotelBookingSystem.model.Payment;

import java.math.BigDecimal;

public interface PaymentServiceImpl {
    Payment createPayment(Long bookingId, BigDecimal amount, String method);
    Payment updatePayment(String transactionRef, String status);
}
