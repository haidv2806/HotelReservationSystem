package com.example.HotelBookingSystem.service;

import com.example.HotelBookingSystem.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CustomerServiceImpl {
    Page<Customer> findAll(Pageable pageable);

    Optional<Customer> findByPhone(String phone);

    Customer save(Customer customer);

    void delete(Integer id);

    Optional<Customer> updateStatus(Integer id, Customer.Status status);

    Optional<Customer> findCustomer(Long id);

    Customer createCustomer(String name, String phone);
}
