package com.example.HotelBookingSystem.controller;

import com.example.HotelBookingSystem.dto.CustomerDTO;
import com.example.HotelBookingSystem.model.Customer;
import com.example.HotelBookingSystem.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @PutMapping("/customer_status")
    public ResponseEntity<Customer> ChangeCustomerStatus(@RequestBody CustomerDTO dto) {
        return customerService.updateStatus(dto.getCustomerId(), dto.getStatus())
                .map(customer -> ResponseEntity.ok(customer))
                .orElse(ResponseEntity.notFound().build());
    }

}
