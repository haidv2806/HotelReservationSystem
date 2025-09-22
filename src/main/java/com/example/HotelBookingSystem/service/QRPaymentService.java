package com.example.HotelBookingSystem.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class QRPaymentService {
    private static final String QR_API = "https://qr.sepay.vn/img";

    public String generateQR(String bankCode, String accountNo,
                             BigDecimal amount, String description) {
        return QR_API + "?acc=" + accountNo
                + "&bank=" + bankCode
                + "&amount=" + (amount != null ? amount : "")
                + "&des=" + description
                + "&template=compact";
    }
}
