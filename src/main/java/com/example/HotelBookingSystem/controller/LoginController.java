package com.example.HotelBookingSystem.controller;

import com.example.HotelBookingSystem.model.Admin;
import com.example.HotelBookingSystem.dto.LoginRequest;
import com.example.HotelBookingSystem.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    @Autowired
    private AdminService adminService;

    // Xử lý form đăng nhập từ Thymeleaf
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        Model model) {

        Admin result = adminService.login(username, password);

        if (result != null) {
            // Đăng nhập thành công → chuyển sang trang quản lý phòng
            return "redirect:/dashboard/room";
        } else {
            // Sai tài khoản → hiển thị lại trang login kèm thông báo lỗi
            model.addAttribute("error", "Tên đăng nhập hoặc mật khẩu không đúng!");
            return "login";
        }
    }

    // ✅ API JSON riêng (nếu cần gọi bằng Postman)
    @PostMapping("/api/login")
    @ResponseBody
    public boolean apiLogin(@RequestBody LoginRequest loginRequest) {
        Admin result = adminService.login(loginRequest.getName(), loginRequest.getPassword());
        return result != null;
    }
}
