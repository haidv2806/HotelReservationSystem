package com.example.HotelBookingSystem.repository;

import com.example.HotelBookingSystem.model.Admin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin,Long> {
    Page<Admin> findAll(Pageable pageable);
    Admin findByAdminNameAndPassword(String admin_name, String password);
    Optional<Admin> findByAdminName(String adminName);
}
