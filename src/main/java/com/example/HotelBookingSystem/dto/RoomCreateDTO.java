package com.example.HotelBookingSystem.dto;

import com.example.HotelBookingSystem.model.Room;

public class RoomCreateDTO {
    private String roomName;
    private String description;
    private String img;
    private String type;
    private Double price;
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

    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }

    public Room.Status getStatus() {
        return status;
    }
    public void setStatus(Room.Status status) {
        this.status = status;
    }
}
