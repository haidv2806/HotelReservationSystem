package com.example.HotelBookingSystem.service;

import com.example.HotelBookingSystem.dto.ManageRoomDTO;
import com.example.HotelBookingSystem.model.Admin;
import com.example.HotelBookingSystem.model.ManageRoom;
import com.example.HotelBookingSystem.model.Room;
import com.example.HotelBookingSystem.repository.AdminRepository;
import com.example.HotelBookingSystem.repository.ManageRoomRepository;
import com.example.HotelBookingSystem.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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
        if (m.getRoom() != null) {
            dto.setRoomId(m.getRoom().getRoomId());
            dto.setRoomName(m.getRoom().getRoomName());
        }
        dto.setStatus(m.getStatus());
        return dto;
    }
    @Override
    public List<ManageRoomDTO> getAll() {
        return manageRoomRepo.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public Page<ManageRoomDTO>getAllManageRooms(LocalDate start, LocalDate end, Pageable pageable){
        Page<ManageRoom> pageResult = manageRoomRepo.searchManages(start, end, pageable);

        return pageResult.map(manageRoom ->  {
            ManageRoomDTO dto = new ManageRoomDTO();

            dto.setManageRoomId(manageRoom.getManageRoomId());
            dto.setRoomName(manageRoom.getRoom() != null ? manageRoom.getRoom().getRoomName() : null);
            dto.setStartDate(manageRoom.getStartDate());
            dto.setEndDate(manageRoom.getEndDate());
            dto.setNote(manageRoom.getNote());
            dto.setStatus(manageRoom.getStatus());

            return dto;
        });
    }

    @Override
    public ManageRoomDTO getById(Integer id) {
        ManageRoom m = manageRoomRepo.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
        return toDTO(m);
    }
    @Override
    public ManageRoomDTO create(
            LocalDate startDate, LocalDate endDate, Integer roomId, String note, String status
    ) {
        // 1️⃣ Kiểm tra ngày bắt đầu & kết thúc
        if (startDate.isAfter(endDate)) {
            throw new RuntimeException("❌ Start date must be before or equal to end date");
        }

        // 2️⃣ Kiểm tra trùng lịch ManageRoom hoặc Booking
        boolean hasOverlap = manageRoomRepo.existsManageRoomOverlap(roomId, startDate, endDate)
                || manageRoomRepo.existsBookingOverlap(roomId, startDate, endDate);

        if (hasOverlap) {
            throw new RuntimeException("❌ Room already has schedule or booking in this date range");
        }


        Room room = roomRepo.findById(roomId).orElseThrow(() -> new RuntimeException("Room not found"));


        ManageRoom entity = new ManageRoom();
        entity.setStartDate(startDate);
        entity.setEndDate(endDate);
        entity.setNote(note);
        entity.setRoom(room);
        entity.setStatus(ManageRoom.Status.valueOf(status.toUpperCase()));

        ManageRoom saved = manageRoomRepo.save(entity);


        return toDTO(saved);
    }

    @Override
    public ManageRoomDTO update(Integer id, ManageRoomDTO dto) {
        ManageRoom m = manageRoomRepo.findById(id).orElseThrow(() -> new RuntimeException("Not found"));


        if (dto.getStartDate().isAfter(dto.getEndDate())) {
            throw new RuntimeException("Start date must be before or equal to end date");
        }


        if (manageRoomRepo.existsManageRoomOverlapUpdate(dto.getRoomId(), dto.getStartDate(), dto.getEndDate(), dto.getManageRoomId())
                || manageRoomRepo.existsBookingOverlap(dto.getRoomId(), dto.getStartDate(), dto.getEndDate())) {
            throw new RuntimeException("Room already has schedule or booking in this date range");
        }

        m.setStartDate(dto.getStartDate());
        m.setEndDate(dto.getEndDate());
        m.setNote(dto.getNote());

        return toDTO(manageRoomRepo.save(m));
    }

    @Override
    public ManageRoomDTO updateStatus (Integer id, ManageRoom.Status status ){
        ManageRoom manageRoom = manageRoomRepo.findById(id).orElseThrow(()-> new RuntimeException("Not found"));
        manageRoom.setStatus(status);

        return  toDTO(manageRoomRepo.save(manageRoom));
    }

    @Override
    public void delete(Integer id) {
        ManageRoom m = manageRoomRepo.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
        manageRoomRepo.delete(m);
    }
}