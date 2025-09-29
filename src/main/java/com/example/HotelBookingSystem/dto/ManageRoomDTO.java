package com.example.HotelBookingSystem.dto;

import com.example.HotelBookingSystem.model.ManageRoom;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDate;

public class ManageRoomDTO {
    private Integer manageRoomId;
    private String createAt;
    private LocalDate startDate;
    private LocalDate endDate;
    private String note;
    private Integer roomId;
    private ManageRoom.Status status;

    // getters & setters
    public Integer getManageRoomId() { return manageRoomId; }
    public void setManageRoomId(Integer manageRoomId) { this.manageRoomId = manageRoomId; }
    public String getCreateAt() { return createAt; }
    public void setCreateAt(String createAt) { this.createAt = createAt; }
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
    public Integer getRoomId() { return roomId; }
    public void setRoomId(Integer roomId) { this.roomId = roomId; }
    public ManageRoom.Status getStatus() { return status; }
    public void setStatus(ManageRoom.Status status) { this.status = status; }
}
