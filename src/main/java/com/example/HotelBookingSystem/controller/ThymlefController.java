package com.example.HotelBookingSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.HotelBookingSystem.repository.CustomerRepository;
import com.example.HotelBookingSystem.service.RoomService;

@Controller
public class ThymlefController {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private RoomService roomService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("customers", customerRepository.findAll());
        model.addAttribute("message", "Xin chào Thymeleaf!");
        return "index"; // trỏ tới file templates/index.html
    }

    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable Integer id) {
        model.addAttribute("room", roomService.getRoomDetail(id));
        return "detail"; // trỏ tới file templates/index.html
    }
}
