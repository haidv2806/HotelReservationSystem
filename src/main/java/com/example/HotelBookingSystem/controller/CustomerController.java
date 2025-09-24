package com.example.HotelBookingSystem.controller;

import com.example.HotelBookingSystem.DTO.CustomerDTO;
import com.example.HotelBookingSystem.model.Admin;
import com.example.HotelBookingSystem.model.Customer;
import com.example.HotelBookingSystem.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/api/customer")
public class CustomerController
{
    @Autowired

    private CustomerService customerService;

    @GetMapping("/dashboard")
    public  String customerDashboard(Model model, Pageable pageable)
    {
        model.addAttribute("customers",customerService.findAll(pageable).getContent());
        return "customer_dashboard";
    }
    @PutMapping("/customer_status")
    @ResponseBody
//
    public ResponseEntity<Customer> ChangeCustomerStatus(@RequestBody CustomerDTO dto) {
        return customerService.updateStatus(dto.getCustomerId(), dto.getStatus())
                .map(customer -> ResponseEntity.ok(customer))
                .orElse(ResponseEntity.notFound().build());
    }

}
