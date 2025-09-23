package com.example.HotelBookingSystem.service;

import com.example.HotelBookingSystem.model.Room;
import com.example.HotelBookingSystem.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoomService implements RoomServiceImpl {
    @Autowired
    private RoomRepository roomRepository;

    @Override
    public Optional<Room> findRoom(Long id) {
        return roomRepository.findById(id);
    }

    @Override
    public Page<Room> findAll(Pageable pageable) {
        return roomRepository.findAll(pageable);
    }

    @Override
    public Room save(Room room) {
        return roomRepository.save(room);
    }

    @Override
    public Room update(Long id, Room newRoom) {
        return roomRepository.findById(id)
                .map(room -> {
                    room.setRoomName(newRoom.getRoomName());
                    room.setType(newRoom.getType());
                    room.setPrice(newRoom.getPrice());
                    room.setDescription(newRoom.getDescription());
                    room.setImg(newRoom.getImg());
                    room.setStatus(newRoom.getStatus());
                    return roomRepository.save(room);
                })
                .orElse(null);
    }

    @Override
    public void delete(Long id) {
        roomRepository.findById(id).ifPresent(room -> {
            room.setStatus(Room.Status.deleted);
            roomRepository.save(room);
        });
    }


    @Override
    public String deleteHandle(Long id) {
        Optional<Room> result = this.findRoom(id);

        if (result.isPresent()) {
            Room room = result.get();
            if (room.getStatus() == Room.Status.deleted) {
                return "Phòng đã bị xóa trước đó";
            }
            this.delete(id); // gọi hàm set status = deleted
            return "Xóa thành công";
        } else {
            return "Không tồn tại phòng";
        }
    }
}

