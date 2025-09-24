package com.example.HotelBookingSystem.controller;

import com.example.HotelBookingSystem.model.Payment;
import com.example.HotelBookingSystem.service.PaymentService;
import com.example.HotelBookingSystem.service.QRPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.StandardReflectionParameterNameDiscoverer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private QRPaymentService qrPaymentService;

    @PutMapping("/confirm/{id}")
    public ResponseEntity<Payment> confirmPayment(
            @PathVariable("id") Long id,
            @RequestParam("status") Payment.PaymentStatus status) {
        Payment p = paymentService.updatePayment(id, status);
        return ResponseEntity.ok(p);
    }
}
