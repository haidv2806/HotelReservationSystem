package com.example.HotelBookingSystem.controller;

import com.example.HotelBookingSystem.DTO.CustomerDTO;
import com.example.HotelBookingSystem.model.Admin;
import com.example.HotelBookingSystem.model.Customer;
import com.example.HotelBookingSystem.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/customer")
public class CustomerController
{
    @Autowired
    private CustomerService customerService;
    @PutMapping("/customer_status")
    public Optional<Customer> ChangeCustomerStatus(@RequestBody CustomerDTO dto)
    {
        //kiem tra admin_id
//        return "cap nhat thanh cong";
        return customerService.updateStatus(dto.getCustomerId(),dto.getStatus());
    }
}
