package com.example.HotelBookingSystem.service;

import com.example.HotelBookingSystem.model.Admin;
import com.example.HotelBookingSystem.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class AdminService implements AdminServiceImpl{
    @Autowired
    private AdminRepository adminRepository;

    @Override
    public Optional<Admin> findAdmin(Long id) {
        return Optional.empty();
    }

    @Override
    public Page<Admin> findAll(Pageable pageable) {
        return adminRepository.findAll(pageable);
    }

    @Override
    public Admin save() {
        return null;
    }

    @Override
    public Admin update() {
        return null;
    }

    @Override
    public void delete() {
    }

    @Override
    public Admin login(String admin_name, String password) {return adminRepository.findByAdminNameAndPassword(admin_name, password);}

}
