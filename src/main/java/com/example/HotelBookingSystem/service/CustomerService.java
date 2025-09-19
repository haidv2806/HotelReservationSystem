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
    public Page<Customer> findAll(Pageable pageable) {
        return customerRepository.findAll(pageable);
    }

    @Override
    public Optional<Customer> findByPhone(String phone) {
        return customerRepository.findByPhone(phone);
    }

    @Override
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public void delete(Integer id) {
        customerRepository.deleteById(id);
    }

    @Override
    public Optional<Customer> updateStatus(Integer id, String status) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            customer.setStatus(status);
            customerRepository.save(customer);
            return Optional.of(customer);
        }
        return Optional.empty();
    }
}
