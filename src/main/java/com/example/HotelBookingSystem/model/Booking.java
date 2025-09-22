package com.example.HotelBookingSystem.model;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "Booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_booking;

    @Column(name = "create_at", updatable = false, insertable = false)
    private LocalDateTime create_at;

    @Column(name = "checkin_date", nullable = false)
    private LocalDate checkin_date;

    @Column(name = "checkout_date", nullable = false)
    private LocalDate checkout_date;

    @Column(name = "note")
    private String note;

    @Column(name = "total_price", nullable = false)
    private BigDecimal total_price;

    @Column(name = "status", nullable = false)
    private String status = "PENDING"; // ENUM('PENDING','CONFIRMED','CHECKED_IN','CHECKED_OUT','CANCELLED')

    // --- FK ---
    @Column(name = "admin_id")
    private Integer admin_id;

    @Column(name = "room_id")
    private Integer room_id;

    @Column(name = "customer_id")
    private Integer customer_id;

    // Getter & Setter
    public Integer getId_booking() {
        return id_booking;
    }

    public void setId_booking(Integer id_booking) {
        this.id_booking = id_booking;
    }

    public LocalDateTime getCreate_at() {
        return create_at;
    }

    public void setCreate_at(LocalDateTime create_at) {
        this.create_at = create_at;
    }

    public LocalDate getCheckin_date() {
        return checkin_date;
    }

    public void setCheckin_date(LocalDate checkin_date) {
        this.checkin_date = checkin_date;
    }

    public LocalDate getCheckout_date() {
        return checkout_date;
    }

    public void setCheckout_date(LocalDate checkout_date) {
        this.checkout_date = checkout_date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public BigDecimal getTotal_price() {
        return total_price;
    }

    public void setTotal_price(BigDecimal total_price) {
        this.total_price = total_price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(Integer admin_id) {
        this.admin_id = admin_id;
    }

    public Integer getRoom_id() {
        return room_id;
    }

    public void setRoom_id(Integer room_id) {
        this.room_id = room_id;
    }

    public Integer getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(Integer customer_id) {
        this.customer_id = customer_id;
    }
}
