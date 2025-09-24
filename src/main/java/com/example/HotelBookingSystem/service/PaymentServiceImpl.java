package com.example.HotelBookingSystem.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.HotelBookingSystem.model.Payment;

public interface PaymentServiceImpl {
    Optional<Payment> findPayment(Long id);

    Page<Payment> findAll(Pageable pageable);

    Payment save();

    Payment update();

    void delete();

    Payment updatePayment(Long id, Payment.PaymentStatus status);
}
