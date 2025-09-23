package com.example.HotelBookingSystem.controller;

import com.example.HotelBookingSystem.dto.RoomSearchRequest;
import com.example.HotelBookingSystem.model.Room;
import com.example.HotelBookingSystem.service.RoomService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class FindRoomController {
    @Autowired
    private RoomService roomService;

    @GetMapping("/room")
    public List<Room> getAllRoom()
    {
        return roomService.findAll(PageRequest.of(0, 100))
                .getContent();
    }

    @PostMapping("/searchRoom")
    public List<Room> searchRoom(@RequestBody RoomSearchRequest request)
    {
        return roomService.searchRooms(request.getRoomType(), request.getCheckInDate(), request.getCheckOutDate(), request.getMinPrice(), request.getMaxPrice(), PageRequest.of(0, 100))
                .getContent();
    }
}
