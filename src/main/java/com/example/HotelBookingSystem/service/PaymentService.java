package com.example.HotelBookingSystem.service;

import com.example.HotelBookingSystem.model.Payment;
import com.example.HotelBookingSystem.model.Payment.PaymentStatus;
import com.example.HotelBookingSystem.repository.PaymentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class PaymentService implements PaymentServiceImpl {
    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public Optional<Payment> findPayment(Long id) {
        return paymentRepository.findById(id);
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

@Override
public Payment updatePayment(Long id, Payment.PaymentStatus status) {
    Optional<Payment> p = this.findPayment(id);

    if (p.isEmpty()) { // ❌ Không tìm thấy thì báo lỗi
        throw new RuntimeException("Không tìm được thông tin thanh toán với id = " + id);
    }

    Payment payment = p.get(); // ✅ Lúc này chắc chắn có
    payment.setStatus(status);
    return paymentRepository.save(payment);
}

}
