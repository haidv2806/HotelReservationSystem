package com.example.HotelBookingSystem.service;

import com.example.HotelBookingSystem.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Optional;

public interface CustomerServiceImpl {
    Optional<Customer> findCustomer(Long id);

    Page<Customer> findAll(Pageable pageable);

    Customer save();

    Customer update();

    void delete();

    Optional<Customer> findByPhone(String phone);

    Customer createCustomer(String name, String phone);
}
