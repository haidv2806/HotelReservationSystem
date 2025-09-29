package com.example.HotelBookingSystem.service;

import com.example.HotelBookingSystem.dto.ManageRoomDTO;

import java.util.List;

public interface ManageRoomServiceImpl {
    List<ManageRoomDTO> getAll();
    ManageRoomDTO getById(Integer id);
    ManageRoomDTO create(ManageRoomDTO dto, String currentAdmin);
    ManageRoomDTO update(Integer id, ManageRoomDTO dto, String currentAdmin);
    void delete(Integer id);
}
