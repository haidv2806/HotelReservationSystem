package com.example.HotelBookingSystem.controller;

import com.example.HotelBookingSystem.config.JwtUtil;
import com.example.HotelBookingSystem.dto.LoginRequest;
import com.example.HotelBookingSystem.model.Admin;
import com.example.HotelBookingSystem.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Admin result = adminService.login(loginRequest.getName(), loginRequest.getPassword());

        if (result != null) {
            String token = JwtUtil.generateToken(result.getAdminName());
            return ResponseEntity.ok(token);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed");
        }
    }
}
