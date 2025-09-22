package com.example.HotelBookingSystem.controller;

import com.example.HotelBookingSystem.model.Payment;
import com.example.HotelBookingSystem.service.PaymentService;
import com.example.HotelBookingSystem.service.QRPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.StandardReflectionParameterNameDiscoverer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @PostMapping("/create")
    public ResponseEntity<Map<String, String>> createPayment(@RequestBody Map<String, String> payload) {
        Long bookingId = Long.parseLong(payload.get("bookingId"));
        BigDecimal amount = new BigDecimal(payload.get("amount"));
        String method = payload.get("method");
        Payment payment = paymentService.createPayment(bookingId, amount, method);
        String qrUrl = null;
        if ("ONLINE".equalsIgnoreCase(method)) {
            qrUrl = qrPaymentService.generateQR(
                    "VCB",
                    "0123456789",
                    amount,
                    "BOOKING" + bookingId
            );
        }
        return ResponseEntity.ok(Map.of(
                "paymentId", payment.getId_payment().toString(),
                "status", payment.getStatus(),
                "qrUrl", qrUrl != null ? qrUrl : ""
        ));
    }

    @PostMapping("confirm")
    public ResponseEntity<Payment> confirmPayment(@RequestBody Map<String, String> payload) {
        String transactionRef = payload.get("transactionRef");
        String status = payload.get("status"); // PAID / REFUNDED

        Payment p = paymentService.updatePayment(transactionRef, status);
        return ResponseEntity.ok(p);
    }
}

