package com.example.HotelBookingSystem.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.HotelBookingSystem.dto.ChatRequest;
import com.example.HotelBookingSystem.dto.RoomDetailResponse; // Cần import DTO này

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors; // Cần import này

@Service
public class ChatService {
    @Autowired
    private RoomService roomService;

    private final ChatClient chatClient;

    public ChatService(ChatClient.Builder builder) {
        chatClient = builder.build();
    }

    public String chat(ChatRequest request) {
        // Lấy tất cả chi tiết phòng
        List<RoomDetailResponse> roomDetails = roomService.getAllRoomDetails();

        // Định dạng dữ liệu phòng thành chuỗi JSON/String có cấu trúc
        String roomData = roomDetails.stream()
                .map(r -> {
                    String blockDatesString = r.getBlockDate().stream()
                            .map(Object::toString)
                            .collect(Collectors.joining(", "));

                    return String.format(
                            "{ID: %d, Tên: %s, Loại: %s, Giá/đêm: %.2f, Mô tả: %s, Ngày bị chặn: [%s]}",
                            r.getRoomId(),
                            r.getRoomName(),
                            r.getType(),
                            r.getPrice(),
                            r.getDescription(),
                            blockDatesString);
                })
                .collect(Collectors.joining("\n---\n"));
        List<Message> messages = new ArrayList<>();

        // 1. Thêm System Message Động
        String systemInstruction = String.format(
                """
                            Bạn là một **Chuyên gia Đặt phòng** thân thiện, chu đáo và siêu hiệu quả tại Royal Hotel.
                            Nhiệm vụ của bạn là **xử lý nhanh chóng các yêu cầu đặt phòng**, sử dụng **Dữ liệu phòng hiện tại** để tìm và gợi ý các lựa chọn tốt nhất cho khách hàng.

                            **Dữ liệu phòng hiện tại:**
                            %s

                            **Quy tắc Giao tiếp & Ưu tiên Hành động:**

                            1.  **Luôn giữ giọng điệu ấm áp, cá nhân hóa** (dùng từ 'bạn', 'chúng tôi').
                            2.  **Ưu tiên Gợi ý:** Khi nhận được yêu cầu tìm kiếm, ngay lập tức sử dụng dữ liệu để **chọn 2-3 phòng phù hợp nhất** (khớp về số lượng khách và giá) và trình bày dưới dạng gợi ý rõ ràng, kèm theo giá.
                            3.  **BẮT BUỘC SỬ DỤNG HYPERLINK:** Khi liệt kê tên phòng gợi ý, bạn phải sử dụng cú pháp Markdown: **[Tên phòng đầy đủ](http://localhost:8080/detail/<roomid>)**. Thay thế `<roomid>` bằng ID thực tế (ví dụ: ID: 401).
                            4.  **Tối giản câu hỏi:** Chỉ hỏi thêm thông tin (như Loại phòng, Ngân sách) khi không tìm được bất kỳ phòng nào phù hợp, hoặc khi người dùng yêu cầu so sánh.
                            5.  **Xử lý ngày:** Luôn kiểm tra `Ngày bị chặn` trong dữ liệu phòng. Nếu ngày khách chọn bị chặn, phải thông báo ngay lập tức và gợi ý các phòng khác/ngày khác.
                            6.  **KHÔNG** đưa ra ý kiến cá nhân. **CHỈ** sử dụng dữ liệu phòng được cung cấp ở trên.
                        """,
                roomData);

        messages.add(new SystemMessage(systemInstruction));

        // 2. Thêm Lịch sử trò chuyện từ Client (logic giữ nguyên)
        if (request.history() != null) {
            for (String historicalMessage : request.history()) {
                if (historicalMessage.toUpperCase().startsWith("USER:")) {
                    messages.add(new UserMessage(historicalMessage.substring(5).trim()));
                } else if (historicalMessage.toUpperCase().startsWith("ASSISTANT:")) {
                    messages.add(new AssistantMessage(historicalMessage.substring(10).trim()));
                }
            }
        }

        // 3. Thêm tin nhắn mới nhất của người dùng
        messages.add(new UserMessage(request.message()));

        // Tạo Prompt và gọi API
        Prompt prompt = new Prompt(messages);

        return chatClient
                .prompt(prompt)
                .call()
                .content();
    }
}