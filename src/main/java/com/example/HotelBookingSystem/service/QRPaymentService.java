package com.example.HotelBookingSystem.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class QRPaymentService {
    private static final String QR_API = "https://qr.sepay.vn/img";
    @Value("${qr.accountNo}")
    private String accountNo;

    @Value("${qr.bankCode}")
    private String bankCode;
    public String generateQR(
                             BigDecimal amount, String description) {
        return QR_API
                + "?acc=" + (accountNo != null ? accountNo.trim() : "")
                + "&bank=" + (bankCode != null ? bankCode.trim() : "")
                + "&amount=" + (amount != null ? amount.toString().trim() : "")
                // + "&des=" + (description != null ? description.trim() : "")
                + "&template=compact";
    }
}
