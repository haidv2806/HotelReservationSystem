package com.example.HotelBookingSystem.controller;

import com.example.HotelBookingSystem.dto.*;
import com.example.HotelBookingSystem.service.ManageRoomService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;


import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/manage-rooms")
public class ManageRoomController {
    @Autowired
    private ManageRoomService manageRoomService;

    public ManageRoomController (ManageRoomService manageRoomService){
        this.manageRoomService = manageRoomService;
    }

    //Lấy toàn bộ danh sách yêu cầu ManageRoom
    @GetMapping("")
    public ResponseEntity<List<ManageRoomDTO>> getAll() {
        return ResponseEntity.ok(manageRoomService.getAll());
    }
    //Lấy chi tiết yêu cầu theo ID
    @GetMapping("/{id}")
    public ResponseEntity<ManageRoomDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(manageRoomService.getById(id));
    }

    @PostMapping("/apiv1")
    public ResponseEntity<ManageRoomDTO> create(@Valid @RequestBody ManageRoomRequest req) {

        ManageRoomDTO res = manageRoomService.create(
                req.getStartDate(),
                req.getEndDate(),
                req.getRoomId(),
                req.getNote(),
                req.getStatus()
        );

        return ResponseEntity.ok(res);
    }
    //
    @PostMapping("/create")
    public Page<ManageRoomDTO> getAllManage(@RequestBody ManageRoomDTO dto){
        return manageRoomService.getAllManageRooms(dto.getStartDate(),dto.getEndDate(), PageRequest.of(0, 10));
    }
    //Cập nhật yêu cầu ManageRoom
    @PutMapping("/{id}")
    public ResponseEntity<ManageRoomDTO> update(@PathVariable("id") Integer manageRoomId, @Valid @RequestBody ManageRoomDTO dto){
        return ResponseEntity.ok(manageRoomService.update(manageRoomId, dto));
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<ManageRoomDTO> updateStatus(@PathVariable("id") Integer manageRoomId, @Valid @RequestBody ManageRoomDTO dto){
        return ResponseEntity.ok(manageRoomService.updateStatus(manageRoomId, dto.getStatus()));
    }

    //Xóa một yêu cầu ManageRoom
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        manageRoomService.delete(id);
        return ResponseEntity.noContent().build();
    }
}