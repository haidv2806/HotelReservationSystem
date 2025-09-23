package com.example.HotelBookingSystem.controller;

import com.example.HotelBookingSystem.model.Admin;
import com.example.HotelBookingSystem.dto.LoginRequest;
import com.example.HotelBookingSystem.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class LoginController {
    @Autowired
    private AdminService adminService;
    @PostMapping("/login")
    public boolean login(@RequestBody LoginRequest loginRequest){
        Admin result = adminService.login(loginRequest.getName(), loginRequest.getPassword());
        return result != null;

    }
}
