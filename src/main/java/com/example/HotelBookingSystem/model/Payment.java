package com.example.HotelBookingSystem.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "Payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_payment")
    private Long id_payment;

    @Column(name = "id_booking", nullable = false)
    private Long id_booking;

    @Column(name = "payment_method", nullable = false)
    private String payment_method = "CASH"; // ENUM('CASH','CARD','ONLINE','VOUCHER')

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "paid_at", insertable = false, updatable = false)
    private LocalDateTime paid_at;

    @Column(name = "status", nullable = false)
    private String status = "PENDING"; // ENUM('PENDING','PAID','REFUNDED')

    @Column(name = "transaction_ref")
    private String transaction_ref;

    public Long getId_payment() {
        return id_payment;
    }

    public void setId_payment(Long id_payment) {
        this.id_payment = id_payment;
    }

    public Long getId_booking() {
        return id_booking;
    }

    public void setId_booking(Long id_booking) {
        this.id_booking = id_booking;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getPaid_at() {
        return paid_at;
    }

    public void setPaid_at(LocalDateTime paid_at) {
        this.paid_at = paid_at;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTransaction_ref() {
        return transaction_ref;
    }

    public void setTransaction_ref(String transaction_ref) {
        this.transaction_ref = transaction_ref;
    }
}
