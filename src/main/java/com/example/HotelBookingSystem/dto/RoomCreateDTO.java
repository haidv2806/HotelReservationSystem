package com.example.HotelBookingSystem.dto;

import java.math.BigDecimal;

import com.example.HotelBookingSystem.model.Room;

public class RoomCreateDTO {
    private String roomName;
    private String description;
    private String img;
    private String type;
    private BigDecimal price;
    private Room.Status status; // có thể để null => mặc định available

    // Getters & Setters
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

    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Room.Status getStatus() {
        return status;
    }
    public void setStatus(Room.Status status) {
        this.status = status;
    }
}
