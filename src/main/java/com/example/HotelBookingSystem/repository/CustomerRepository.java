package com.example.HotelBookingSystem.repository;

import com.example.HotelBookingSystem.model.Customer;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Page<Customer> findAll(Pageable pageable);

    Optional<Customer> findByPhone(String phone);
    @Query("SELECT c FROM Customer c WHERE LOWER(c.customerName) LIKE %:keyword% OR LOWER(c.phone) LIKE %:keyword%")
    List<Customer> searchByKeyword(@Param("keyword") String keyword);
    @Query("SELECT c FROM Customer c WHERE LOWER(c.customerName) LIKE %:keyword% OR LOWER(c.phone) LIKE %:keyword%")
    Page<Customer> searchByKeywordPaged(@Param("keyword") String keyword, Pageable pageable);

}
