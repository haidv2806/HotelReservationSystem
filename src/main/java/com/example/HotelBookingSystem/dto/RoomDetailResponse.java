package com.example.HotelBookingSystem.dto;

import java.util.List;

import java.time.LocalDate;

public class RoomDetailResponse {
    private Integer roomId;
    private String roomName;
    private String description;
    private String img;
    private String type;
    private Double price;
    private List<LocalDate> blockDate;

    public RoomDetailResponse(Integer roomId ,String roomName, String description, String img, String type, Double price,
            List<LocalDate> blockDate) {
        this.roomId =roomId;
        this.roomName = roomName;
        this.description = description;
        this.img = img;
        this.type = type;
        this.price = price;
        this.blockDate = blockDate;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    // Getters
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

    public List<LocalDate> getBlockDate() {
        return blockDate;
    }

    public void setBlockDate(List<LocalDate> blockDate) {
        this.blockDate = blockDate;
    }
}
