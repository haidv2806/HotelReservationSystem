package com.example.HotelBookingSystem.service;

import com.example.HotelBookingSystem.dto.ManageRoomDTO;
import com.example.HotelBookingSystem.model.ManageRoom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface ManageRoomServiceImpl {
    List<ManageRoomDTO> getAll();
    ManageRoomDTO getById(Integer id);
    ManageRoomDTO create(LocalDate startDate, LocalDate endDate, Integer roomId, String note, String status);
    ManageRoomDTO update(Integer id, ManageRoomDTO dto);
    void delete(Integer id);
    ManageRoomDTO updateStatus(Integer id, ManageRoom.Status status);

    Page<ManageRoomDTO>getAllManageRooms(LocalDate start, LocalDate end, Pageable pageable);
}