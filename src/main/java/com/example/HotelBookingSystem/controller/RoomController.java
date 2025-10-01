package com.example.HotelBookingSystem.controller;

import com.example.HotelBookingSystem.dto.RoomCreateDTO;
import com.example.HotelBookingSystem.dto.RoomDetailResponse;
import com.example.HotelBookingSystem.dto.RoomSearchRequest;
import com.example.HotelBookingSystem.model.Room;
import com.example.HotelBookingSystem.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    // Lấy tất cả phòng (ví dụ giới hạn 100 bản ghi)
    @GetMapping
    public List<Room> getAllRooms() {
        return roomService.findAll(PageRequest.of(0, 100)).getContent();
    }

    @PostMapping("/searchRoom")
    public List<Room> searchRoom(@RequestBody RoomSearchRequest request) {
        return roomService
                .searchRooms(request.getRoomType(), request.getCheckInDate(), request.getCheckOutDate(),
                        request.getMinPrice(), request.getMaxPrice(), PageRequest.of(0, 100))
                .getContent();
    }

    @GetMapping("/room/{id}")
    public RoomDetailResponse roomDetail(@PathVariable("id") Integer roomId) {
        return roomService.getRoomDetail(roomId);
    }

    // Lấy phòng theo ID
    // @GetMapping("/{id}")
    // public ResponseEntity<Room> getRoomById(@PathVariable Integer id) {
    //     return roomService.findRoom(id)
    //             .map(ResponseEntity::ok)
    //             .orElse(ResponseEntity.notFound().build());
    // }

    // Thêm phòng mới
    @PostMapping
    public Room createRoom(@RequestBody RoomCreateDTO dto) {
        Room room = new Room();
        room.setRoomName(dto.getRoomName());
        room.setDescription(dto.getDescription());
        room.setImg(dto.getImg());
        room.setType(dto.getType());
        room.setPrice(dto.getPrice());
        room.setStatus(dto.getStatus() != null ? dto.getStatus() : Room.Status.available);
        return roomService.save(room);
    }

    // Cập nhật phòng
    @PutMapping("/{id}")
    public ResponseEntity<Room> updateRoom(@PathVariable Integer id, @RequestBody Room room) {
        Room updated = roomService.update(id, room);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Xóa phòng
    @DeleteMapping("/{id}")
    public String deleteRoom(@PathVariable Integer id) {
        return roomService.deleteHandle(id);
    }
}
