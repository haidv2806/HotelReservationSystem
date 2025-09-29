package com.example.HotelBookingSystem.controller;

import com.example.HotelBookingSystem.dto.ManageRoomDTO;
import com.example.HotelBookingSystem.service.ManageRoomService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    public ResponseEntity<List<ManageRoomDTO>> getAll() {
        return ResponseEntity.ok(manageRoomService.getAll());
    }
    //Lấy chi tiết yêu cầu theo ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    public ResponseEntity<ManageRoomDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(manageRoomService.getById(id));
    }
    //Tạo mới yêu cầu ManageRoom
    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    public ResponseEntity<ManageRoomDTO> create(@Valid @RequestBody ManageRoomDTO dto, Authentication authentication) {
        String admin = authentication.getName();
        return ResponseEntity.ok(manageRoomService.create(dto, admin));
    }
    //Cập nhật yêu cầu ManageRoom
    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    public ResponseEntity<ManageRoomDTO> update(@PathVariable Integer id, @Valid @RequestBody ManageRoomDTO dto, Authentication authentication) {
        String admin = authentication.getName();
        return ResponseEntity.ok(manageRoomService.update(id, dto, admin));
    }
    //Xóa một yêu cầu ManageRoom
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        manageRoomService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
