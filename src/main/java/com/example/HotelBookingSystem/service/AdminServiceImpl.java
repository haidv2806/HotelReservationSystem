package com.example.HotelBookingSystem.service;

import com.example.HotelBookingSystem.model.Admin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Optional;

public interface AdminServiceImpl {
    Optional<Admin> findAdmin(Long id);
    Page<Admin> findAll(Pageable pageable);
    Admin save();
    Admin update();
    void delete();

    Admin login(String admin_name, String password);
}
