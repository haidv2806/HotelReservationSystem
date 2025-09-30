package com.example.HotelBookingSystem.service;

import com.example.HotelBookingSystem.dto.ManageRoomDTO;
import com.example.HotelBookingSystem.model.Admin;
import com.example.HotelBookingSystem.model.ManageRoom;
import com.example.HotelBookingSystem.model.Room;
import com.example.HotelBookingSystem.repository.AdminRepository;
import com.example.HotelBookingSystem.repository.ManageRoomRepository;
import com.example.HotelBookingSystem.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ManageRoomService implements ManageRoomServiceImpl {
    @Autowired
    private ManageRoomRepository manageRoomRepo;
    @Autowired
    private AdminRepository adminRepo;
    @Autowired
    private  RoomRepository roomRepo;

    private ManageRoomDTO toDTO(ManageRoom m) {
        ManageRoomDTO dto = new ManageRoomDTO();
        dto.setManageRoomId(m.getManageRoomId());
        dto.setCreateAt(m.getCreateAt().toString());
        dto.setStartDate(m.getStartDate());
        dto.setEndDate(m.getEndDate());
        dto.setNote(m.getNote());
        dto.setRoomId(m.getRoom().getRoomId());
        dto.setStatus(m.getStatus());
        return dto;
    }
    @Override
    public List<ManageRoomDTO> getAll() {
        return manageRoomRepo.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }


    @Override
    public ManageRoomDTO getById(Integer id) {
        ManageRoom m = manageRoomRepo.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
        return toDTO(m);
    }
    @Override
    public ManageRoomDTO create(ManageRoomDTO dto) {
        if (dto.getStartDate().isAfter(dto.getEndDate())) {
            throw new RuntimeException("Start date must be before or equal to end date");
        }


        if (manageRoomRepo.existsManageRoomOverlap(dto.getRoomId(), dto.getStartDate(), dto.getEndDate())
                || manageRoomRepo.existsBookingOverlap(dto.getRoomId(), dto.getStartDate(), dto.getEndDate())) {
            throw new RuntimeException("Room already has schedule or booking in this date range");
        }



        Room room = roomRepo.findById(dto.getRoomId()).orElseThrow(() -> new RuntimeException("Room not found"));


        ManageRoom m = new ManageRoom();
        m.setStartDate(dto.getStartDate());
        m.setEndDate(dto.getEndDate());
        m.setNote(dto.getNote());
        m.setStatus(dto.getStatus());
        m.setRoom(room);


        return toDTO(manageRoomRepo.save(m));
    }

    @Override
    public ManageRoomDTO update(Integer id, ManageRoomDTO dto) {
        ManageRoom m = manageRoomRepo.findById(id).orElseThrow(() -> new RuntimeException("Not found"));


        if (dto.getStartDate().isAfter(dto.getEndDate())) {
            throw new RuntimeException("Start date must be before or equal to end date");
        }


        if (manageRoomRepo.existsManageRoomOverlap(dto.getRoomId(), dto.getStartDate(), dto.getEndDate())
                || manageRoomRepo.existsBookingOverlap(dto.getRoomId(), dto.getStartDate(), dto.getEndDate())) {
            throw new RuntimeException("Room already has schedule or booking in this date range");
        }

        Room room = roomRepo.findById(dto.getRoomId()).orElseThrow(() -> new RuntimeException("Room not found"));

        m.setStartDate(dto.getStartDate());
        m.setEndDate(dto.getEndDate());
        m.setNote(dto.getNote());
        m.setStatus(dto.getStatus());
        m.setRoom(room);


        return toDTO(manageRoomRepo.save(m));
    }


    @Override
    public void delete(Integer id) {
        ManageRoom m = manageRoomRepo.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
        manageRoomRepo.delete(m);
    }
}

