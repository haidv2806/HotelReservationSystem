package com.example.HotelBookingSystem.service;

import com.example.HotelBookingSystem.dto.ManageRoomDTO;
import com.example.HotelBookingSystem.model.ManageRoom;

import java.util.List;

public interface ManageRoomServiceImpl {
    List<ManageRoomDTO> getAll();
    ManageRoomDTO getById(Integer id);
    ManageRoomDTO create(ManageRoomDTO dto);
    ManageRoomDTO update(Integer id, ManageRoomDTO dto);
    void delete(Integer id);
    ManageRoomDTO updateStatus(Integer id, ManageRoom.Status status);
}
