package com.example.HotelBookingSystem.service;

import com.example.HotelBookingSystem.dto.ManageRoomDTO;

import java.util.List;

public interface ManageRoomServiceImpl {
    List<ManageRoomDTO> getAll();
    ManageRoomDTO getById(Integer id);
    ManageRoomDTO create(ManageRoomDTO dto);
    ManageRoomDTO update(Integer id, ManageRoomDTO dto);
    void delete(Integer id);
}
