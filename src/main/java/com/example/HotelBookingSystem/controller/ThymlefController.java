package com.example.HotelBookingSystem.controller;

import com.example.HotelBookingSystem.dto.BookingResponseDTO;
import com.example.HotelBookingSystem.dto.ManageRoomDTO;
import com.example.HotelBookingSystem.dto.ManageRoomRequest;
import com.example.HotelBookingSystem.service.BookingService;
import com.example.HotelBookingSystem.service.ManageRoomService;
import com.example.HotelBookingSystem.model.Admin;
import com.example.HotelBookingSystem.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.jsoup.Jsoup;

import com.example.HotelBookingSystem.model.Booking;
import com.example.HotelBookingSystem.model.ManageRoom;
import com.example.HotelBookingSystem.model.Room;
import com.example.HotelBookingSystem.repository.BookingRepository;
import com.example.HotelBookingSystem.repository.CustomerRepository;
import com.example.HotelBookingSystem.repository.ManageRoomRepository;
import com.example.HotelBookingSystem.repository.RoomRepository;
import com.example.HotelBookingSystem.service.RoomService;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ThymlefController {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AdminService adminService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private ManageRoomRepository manageRoomRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private BookingService bookingService;

    @Autowired
    private ManageRoomService manageRoomService;

    @GetMapping("/")
    public String index(Model model,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<Room> roomPage = roomService.findAll(PageRequest.of(page, size));

        List<Room> rooms = roomService.findAll(PageRequest.of(page, size)).getContent();
        for (Room r : rooms) {
            r.setDescription(Jsoup.parse(r.getDescription()).text());
        }
        model.addAttribute("rooms", rooms);
        model.addAttribute("customers", customerRepository.findAll());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", roomPage.getTotalPages());

        return "index"; // trỏ tới file templates/index.html
    }

    @GetMapping("/searchRoom")
    public String searchRoom(
            @RequestParam(required = false) String roomType,
            @RequestParam(required = false) LocalDate checkInDate,
            @RequestParam(required = false) LocalDate checkOutDate,
            @RequestParam(required = false, defaultValue = "100000") BigDecimal minPrice,
            @RequestParam(required = false, defaultValue = "5000000") BigDecimal maxPrice,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {

        if (roomType != null) {
            roomType = roomType.trim();
            if (roomType.isEmpty()) {
                roomType = null;
            }
        }

        Page<Room> roomPage = roomService.searchRooms(
                roomType,
                checkInDate,
                checkOutDate,
                minPrice,
                maxPrice,
                PageRequest.of(page, size));

        roomPage.forEach(r -> r.setDescription(Jsoup.parse(r.getDescription()).text()));

        model.addAttribute("rooms", roomPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", roomPage.getTotalPages());
        model.addAttribute("scrollToCards", true);

        // ✅ giữ lại tham số tìm kiếm
        model.addAttribute("roomType", roomType);
        model.addAttribute("checkInDate", checkInDate);
        model.addAttribute("checkOutDate", checkOutDate);
        model.addAttribute("minPrice", minPrice);
        model.addAttribute("maxPrice", maxPrice);

        return "index";
    }

    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable Integer id) {
        model.addAttribute("room", roomService.getRoomDetail(id));
        return "detail"; // trỏ tới file templates/index.html
    }
    @GetMapping("/detailbooking/{id}")
    public String detailBooking(Model model,
                                @PathVariable Integer id,
                                @RequestParam(required = false) String checkInDate,
                                @RequestParam(required = false) String checkOutDate) {
        // Lấy thông tin phòng
        com.example.HotelBookingSystem.dto.RoomDetailResponse room = roomService.getRoomDetail(id);
        model.addAttribute("room", room);

        // Nếu không có ngày gửi từ query param thì dùng mặc định
        model.addAttribute("checkInDate", checkInDate != null ? checkInDate : LocalDate.now().toString());
        model.addAttribute("checkOutDate", checkOutDate != null ? checkOutDate : LocalDate.now().plusDays(1).toString());

        return "booking_user"; // trỏ tới file booking_user.html
    }

    // Trang booking trực tiếp từ link /booking_user?roomId=xxx&checkInDate=xxx&checkOutDate=xxx
    @GetMapping("/booking_user")
    public String bookingPage(@RequestParam Integer roomId,
                              @RequestParam(required = false) String checkInDate,
                              @RequestParam(required = false) String checkOutDate,
                              Model model) {
        com.example.HotelBookingSystem.dto.RoomDetailResponse room = roomService.getRoomDetail(roomId);
        model.addAttribute("room", room);

        model.addAttribute("checkInDate", checkInDate != null ? checkInDate : LocalDate.now().toString());
        model.addAttribute("checkOutDate", checkOutDate != null ? checkOutDate : LocalDate.now().plusDays(1).toString());

        return "booking_user";
    }
    @GetMapping("/dashboard/customer")
    public String customerDashboard(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("customerId").ascending());
        Page<com.example.HotelBookingSystem.model.Customer> customerPage =
                customerRepository.findAll(pageable);

        model.addAttribute("customers", customerPage.getContent());
        model.addAttribute("currentPage", customerPage.getNumber());
        model.addAttribute("totalPages", customerPage.getTotalPages());

        return "customer";
    }

    @GetMapping("/dashboard/manageroom")
    public ModelAndView showManageRooms(
            @RequestParam(required = false, name = "startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate start,
            @RequestParam(required = false, name = "endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end,
            @PageableDefault(size = 10, sort = "createAt", direction = Sort.Direction.ASC) Pageable pageable,
            Model model
    ) {
        // ✅ Lấy danh sách ManageRoom theo ngày
        Page<ManageRoomDTO> pageResult = manageRoomService.getAllManageRooms(start, end, pageable);

        // ✅ Chuẩn bị dữ liệu cho Thymeleaf
        ModelAndView mav = new ModelAndView("manageroom");
        mav.addObject("manageRooms", pageResult.getContent());
        mav.addObject("currentPage", pageResult.getNumber());
        mav.addObject("totalPages", pageResult.getTotalPages());
        mav.addObject("totalElements", pageResult.getTotalElements());
        mav.addObject("startDate", start);
        mav.addObject("endDate", end);

        return mav;
    }

    @GetMapping("/dashboard/manageroom/create")
    public String showCreateManageRoomPage(Model model) {
        model.addAttribute("manageRoom", new ManageRoomRequest());
        return "fragments/manageroom/manageroom-create"; // ✅ khớp đúng thư mục
    }
    // Nhận form thêm mới (POST)
    @PostMapping("/dashboard/manageroom/create")
    public String createManageRoom(@ModelAttribute ManageRoomRequest request, Model model) {
        try {
            manageRoomService.create(
                    request.getStartDate(),
                    request.getEndDate(),
                    request.getRoomId(),
                    request.getNote(),
                    request.getStatus()
            );
            return "redirect:/dashboard/manageroom";
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("manageRoom", request);
            return "fragments/manageroom/manageroom-create";
        }
    }

    @GetMapping("/dashboard/manageroom/edit/{id}")
    public ModelAndView editForm(@PathVariable Integer id) {
        ManageRoomDTO dto = manageRoomService.getById(id);
        ModelAndView mav = new ModelAndView("fragments/manageroom/manageroom-edit");
        mav.addObject("manageRoom", dto);
        return mav;
    }

    //cap nhat
    @PostMapping("/dashboard/manageroom/update/{id}")
    public String updateManageRoom(@PathVariable Integer id,
                                   @ModelAttribute ManageRoomDTO dto,
                                   RedirectAttributes redirectAttributes) {
        try {
            manageRoomService.update(id, dto);
            redirectAttributes.addFlashAttribute("success", "Cập nhật thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/dashboard/manageroom";

    }


    @GetMapping("/dashboard/bookingconfirm")
    public ModelAndView showBookings(
            @RequestParam(required = false, value = "search") String search,
            @RequestParam(required = false, name = "checkinDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate start,
            @RequestParam(required = false, name = "checkoutDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end,
            @PageableDefault(size = 10, sort = "createAt", direction = Sort.Direction.ASC) Pageable pageable,
            Model model
    ) {
        // ✅ Làm sạch chuỗi tìm kiếm (trim + bỏ chuỗi rỗng)
        if (search != null) {
            search = search.trim();
            if (search.isEmpty()) {
                search = null;
            }
        }

        // ✅ Lấy dữ liệu phân trang
        Page<BookingResponseDTO> pageResult = bookingService.getAllBookingsPaginated(start, end, search, pageable);

        // ✅ Chuẩn bị ModelAndView
        ModelAndView mav = new ModelAndView("bookingconfirm");
        mav.addObject("bookings", pageResult.getContent());
        mav.addObject("currentPage", pageResult.getNumber());
        mav.addObject("totalPages", pageResult.getTotalPages());
        mav.addObject("totalElements", pageResult.getTotalElements());

        // ✅ Giữ lại các tham số tìm kiếm để hiển thị lại trên giao diện
        mav.addObject("search", search);
        mav.addObject("start", start);
        mav.addObject("end", end);

        // ✅ Đánh dấu để tự động cuộn xuống bảng (nếu bạn muốn behavior như "scrollToCards")
        mav.addObject("scrollToTable", true);

        return mav;
    }

//    @GetMapping("/dashboard/room")
//    public String showRooms(Model model) {
//        List<Room> rooms = roomService.findAll(PageRequest.of(0, 100)).getContent();
//        model.addAttribute("rooms", rooms);
//        return "room"; // tìm file room.html
//    }

    // Hiển thị form login
    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @GetMapping("/dashboard/room/addroom")
    public String addRoomPage() {
        return "addroom";
    }

    @GetMapping("/dashboard/room")
    public String viewRoomsFirstPage() {
        return "redirect:/dashboard/room/page/1";
    }

    @GetMapping("/dashboard/room/page/{pageNo}")
    public String listRooms(@PathVariable("pageNo") int pageNo, Model model) {
        int pageSize = 5; // số phòng mỗi trang
        Page<Room> roomPage = roomService.findAll(PageRequest.of(pageNo - 1, pageSize));

        model.addAttribute("rooms", roomPage.getContent());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", roomPage.getTotalPages());

        return "room"; // trỏ đến room.html
    }

    @GetMapping("/dashboard/room/editroom/{id}")
    public String showEditRoom(@PathVariable("id") Integer id, Model model) {
        Room room = roomService.findRoom(id).orElseThrow(() -> new RuntimeException("Room not found"));
        model.addAttribute("room", room);
        return "editroom"; // ✅ đúng với vị trí file templates/editroom.html
    }

//    @GetMapping("/dashboard/room/editroom/{id}")
//    public String showEditRoom() {
//        return "editroom";
//    }
}
