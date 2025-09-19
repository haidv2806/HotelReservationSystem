package com.example.HotelBookingSystem.controller;

import com.example.HotelBookingSystem.model.Admin;
import com.example.HotelBookingSystem.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AdminController {
    @Autowired
    private AdminService adminService;
    @GetMapping
    public List<Admin> getAllAdmin()
    {
        return adminService.findAll(PageRequest.of(0, 100)) // lấy 100 bản ghi
                .getContent();
    }
}
