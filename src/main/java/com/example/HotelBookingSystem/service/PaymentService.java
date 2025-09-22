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
    public Payment createPayment(Integer bookingId, BigDecimal amount, String method) {
        Payment p = new Payment();
        p.setId_booking(bookingId);
        p.setAmount(amount);
        p.setPayment_method(method);
        p.setStatus("PENDING");
        return paymentRepository.save(p);

    }

    @Override
    public Payment updatePayment(String transactionRef, String status) {
        Payment p = paymentRepository.findByTransactionRef(transactionRef).orElseThrow(() -> new RuntimeException("Payment not found"));
        p.setStatus(status);
        return paymentRepository.save(p);
    }
}
