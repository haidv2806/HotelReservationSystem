package com.example.HotelBookingSystem.service;

import com.example.HotelBookingSystem.model.Payment;
import com.example.HotelBookingSystem.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class PaymentService implements PaymentServiceImpl {
    @Autowired
    PaymentRepository   paymentRepository;
    @Override
    public Payment createPayment(Long bookingId, BigDecimal amount, String method) {
        Pay

    }

    @Override
    public Payment updatePayment(String transactionRef, String status) {
        return null;
    }
}
