package com.example.HotelBookingSystem.dto;

import com.example.HotelBookingSystem.model.Customer;

public class CustomerDTO {
    private Integer adminId;
    private Integer customerId;
    private Customer.Status status;
    public CustomerDTO() {}
    // Getter & Setter
    public Integer getAdminId() { return adminId; }
    public void setAdminId(Integer adminId) { this.adminId = adminId; }

    public Integer getCustomerId() { return customerId; }
    public void setCustomerId(Integer customerId) { this.customerId = customerId; }

    public Customer.Status getStatus() { return status; }
    public void setStatus(Customer.Status status) { this.status = status; }
}
