package com.example.HotelBookingSystem.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Room")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private int roomId;

    @Column(name = "room_name", nullable = false, unique = true, length = 50)
    private String roomName;

    @Column(name = "description")
    private String description;

    @Column(name = "img", length = 255)
    private String img;

    @Column(name = "type", nullable = false, length = 100)
    private String type;

    @Column(name = "price", nullable = false)
    private Double price;

    @Enumerated(EnumType.STRING) // ánh xạ ENUM SQL -> Enum Java
    @Column(name = "status", nullable = false)
    private Status status = Status.available; // mặc định

    // --- Enum ánh xạ với ENUM trong DB ---
    public enum Status {
        available,
        deleted
    }

    // --- Getters và Setters ---
    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}