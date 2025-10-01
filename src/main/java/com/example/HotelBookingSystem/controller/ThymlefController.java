package com.example.HotelBookingSystem.controller;
import com.example.HotelBookingSystem.model.Booking;
import com.example.HotelBookingSystem.model.ManageRoom;
import com.example.HotelBookingSystem.repository.BookingRepository;
import com.example.HotelBookingSystem.repository.ManageRoomRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.HotelBookingSystem.repository.CustomerRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.slf4j.LoggerFactory;

import java.util.List;

@Controller
public class ThymlefController {
        @Autowired
        private ManageRoomRepository manageRoomRepository;



//        @GetMapping("/")
//        public String index(Model model) {
//            model.addAttribute("customers", customerRepository.findAll());
//            model.addAttribute("message", "Xin chào Thymeleaf!");
//            return "index";  // trỏ tới file templates/index.html
//        }
    @GetMapping("/")
    public String showManageRooms(Model model) {
        // Lấy tất cả record từ DB
        List<ManageRoom> manageRooms = manageRoomRepository.findAll();
        // Truyền xuống view
        model.addAttribute("managerooms", manageRooms);
        // Trả về index.html (trong đó có include manageroom.html)
        return "index";
    }
}

