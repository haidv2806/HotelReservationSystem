package com.example.HotelBookingSystem.service;

import com.example.HotelBookingSystem.model.Customer;
import com.example.HotelBookingSystem.repository.CustomerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService implements CustomerServiceImpl {
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Optional<Customer> findCustomer(Long id) {
        return Optional.empty();
    }

    @Override
    public Page<Customer> findAll(Pageable pageable) {
        return customerRepository.findAll(pageable);
    }

    @Override
    public Customer save() {
        return null;
    }

    @Override
    public Customer update() {
        return null;
    }

    @Override
    public void delete() {

    }

    @Override
    public Optional<Customer> findByPhone(String phone) {
        return Optional.empty();
    }

    @Override
    public Customer createCustomer(String name, String phone) {
        Customer customer = new Customer();
        customer.setCustomerName(name);
        customer.setPhone(phone);
        // status mặc định là Status.normal
        return customerRepository.save(customer);
    }
}
