package com.example.HotelBookingSystem.controller;

import com.example.HotelBookingSystem.model.Admin;
import com.example.HotelBookingSystem.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.jsoup.Jsoup;

import com.example.HotelBookingSystem.model.Booking;
import com.example.HotelBookingSystem.model.ManageRoom;
import com.example.HotelBookingSystem.model.Room;
import com.example.HotelBookingSystem.repository.BookingRepository;
import com.example.HotelBookingSystem.repository.CustomerRepository;
import com.example.HotelBookingSystem.repository.ManageRoomRepository;
import com.example.HotelBookingSystem.repository.RoomRepository;
import com.example.HotelBookingSystem.service.RoomService;

@Controller
public class ThymlefController {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AdminService adminService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private ManageRoomRepository manageRoomRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private RoomRepository roomRepository;

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

    @GetMapping("/dashboard/cusomer")
    public String index(Model model) {
        model.addAttribute("customers", customerRepository.findAll());
        return "customer"; // trỏ tới file templates/index.html
    }

    @GetMapping("/dashboard/manageroom")
    public String showManageRooms(Model model) {
        // Lấy tất cả record từ DB
        List<ManageRoom> manageRooms = manageRoomRepository.findAll();
        // Truyền xuống view
        model.addAttribute("managerooms", manageRooms);
        // Trả về index.html (trong đó có include manageroom.html)
        return "manageroom";
    }

    @GetMapping("/dashboard/bookingconfirm")
    public String showBookings(Model model) {
        // Lấy tất cả record từ DB
        List<Booking> bookings = bookingRepository.findAll();

        // Truyền xuống view
        model.addAttribute("bookings", bookings);

        // Trả về index.html (trong đó có include bookingconfirm.html)
        return "bookingconfirm";
    }

    @GetMapping("/dashboard/room")
    public String showRooms(Model model) {
        List<Room> rooms = roomService.findAll(PageRequest.of(0, 100)).getContent();
        model.addAttribute("rooms", rooms);
        return "room"; // tìm file room.html
    }

    // Hiển thị form login
    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @GetMapping("/dashboard/room/addroom")
    public String addRoomPage() {
        return "addroom";
    }

    @GetMapping("/dashboard/room/editroom/{id}")
    public String editRoom(@PathVariable("id") Integer id, Model model) {
        Room room = roomService.findRoom(id).orElse(null);
        if (room == null) {
            return "redirect:/dashboard/room";
        }
        model.addAttribute("room", room);
        return "editroom"; // templates/room/editroom.html
    }

}
