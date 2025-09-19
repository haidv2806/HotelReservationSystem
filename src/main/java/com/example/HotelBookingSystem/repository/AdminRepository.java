package com.example.HotelBookingSystem.repository;

import com.example.HotelBookingSystem.model.Admin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin,Long> {
    Page<Admin> findAll(Pageable pageable);

}
