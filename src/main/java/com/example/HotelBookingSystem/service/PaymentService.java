package com.example.HotelBookingSystem.service;

import com.example.HotelBookingSystem.model.Payment;
import com.example.HotelBookingSystem.repository.PaymentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentService implements PaymentServiceImpl {
    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public Optional<Payment> findPayment(Integer id) {
        return Optional.empty();
    }

    @Override
    public Page<Payment> findAll(Pageable pageable) {
        return paymentRepository.findAll(pageable);
    }

    @Override
    public Payment save() {
        return null;
    }

    @Override
    public Payment update() {
        return null;
    }

    @Override
    public void delete() {

    }
}
