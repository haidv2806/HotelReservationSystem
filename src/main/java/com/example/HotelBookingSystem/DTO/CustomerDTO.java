package com.example.HotelBookingSystem.DTO;

public class CustomerDTO {
    private Integer adminId;
    private Integer customerId;
    private String status;
    public CustomerDTO() {}
    // Getter & Setter
    public Integer getAdminId() { return adminId; }
    public void setAdminId(Integer adminId) { this.adminId = adminId; }

    public Integer getCustomerId() { return customerId; }
    public void setCustomerId(Integer customerId) { this.customerId = customerId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
