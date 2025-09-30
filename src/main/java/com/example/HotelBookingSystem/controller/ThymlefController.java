package com.example.HotelBookingSystem.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.HotelBookingSystem.repository.CustomerRepository;
@Controller
public class ThymlefController {
        @Autowired
    private CustomerRepository customerRepository;

        @GetMapping("/")
        public String index(Model model) {
            model.addAttribute("customers", customerRepository.findAll());
            model.addAttribute("message", "Xin chào Thymeleaf!");
            return "index";  // trỏ tới file templates/index.html
        }
    }

