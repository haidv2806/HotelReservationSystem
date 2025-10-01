package com.example.HotelBookingSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.jsoup.Jsoup;

import com.example.HotelBookingSystem.model.Room;
import com.example.HotelBookingSystem.repository.CustomerRepository;
import com.example.HotelBookingSystem.service.RoomService;

@Controller
public class ThymlefController {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private RoomService roomService;

    @GetMapping("/")
    public String index(Model model,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<Room> roomPage = roomService.findAll(PageRequest.of(page, size));

        List<Room> rooms = roomService.findAll(PageRequest.of(page, size)).getContent();
        for (Room r : rooms) {
            r.setDescription(Jsoup.parse(r.getDescription()).text());
        }
        model.addAttribute("rooms", rooms);
        model.addAttribute("customers", customerRepository.findAll());
        model.addAttribute("message", "Xin chào Thymeleaf!");
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", roomPage.getTotalPages());

        return "index"; // trỏ tới file templates/index.html
    }

    @GetMapping("/searchRoom")
    public String searchRoom(
            @RequestParam(required = false) String roomType,
            @RequestParam(required = false) LocalDate checkInDate,
            @RequestParam(required = false) LocalDate checkOutDate,
            @RequestParam(required = false, defaultValue = "100000") BigDecimal minPrice,
            @RequestParam(required = false, defaultValue = "5000000") BigDecimal maxPrice,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {

        if (roomType != null) {
            roomType = roomType.trim();
            if (roomType.isEmpty()) {
                roomType = null;
            }
        }

        Page<Room> roomPage = roomService.searchRooms(
                roomType,
                checkInDate,
                checkOutDate,
                minPrice,
                maxPrice,
                PageRequest.of(page, size));

        roomPage.forEach(r -> r.setDescription(Jsoup.parse(r.getDescription()).text()));

        model.addAttribute("rooms", roomPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", roomPage.getTotalPages());
        model.addAttribute("scrollToCards", true);

        // ✅ giữ lại tham số tìm kiếm
        model.addAttribute("roomType", roomType);
        model.addAttribute("checkInDate", checkInDate);
        model.addAttribute("checkOutDate", checkOutDate);
        model.addAttribute("minPrice", minPrice);
        model.addAttribute("maxPrice", maxPrice);

        return "index";
    }

    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable Integer id) {
        model.addAttribute("room", roomService.getRoomDetail(id));
        return "detail"; // trỏ tới file templates/index.html
    }
}
