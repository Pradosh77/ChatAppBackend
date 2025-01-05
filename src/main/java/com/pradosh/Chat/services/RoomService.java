package com.pradosh.Chat.services;

import com.pradosh.Chat.entities.Message;
import com.pradosh.Chat.entities.Room;
import com.pradosh.Chat.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {
    private final RoomRepository roomRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public Room findRoomByRoomId(String roomId){
        return roomRepository.findByRoomId(roomId);
    }

    public Room save(Room room){
        return roomRepository.save(room);
    }

    public Room createRoom(String roomId) {
        if (roomRepository.findByRoomId(roomId) != null) {
            throw new IllegalArgumentException("Room already exists!");
        }

        Room room = new Room();
        room.setRoomId(roomId);
        return roomRepository.save(room);
    }

    public Room joinRoom(String roomId) {
        Room room = roomRepository.findByRoomId(roomId);
        if (room == null) {
            throw new IllegalArgumentException("Room not found!!");
        }
        return room;
    }

    public List<Message> getMessages(String roomId, int page, int size) {
        Room room = roomRepository.findByRoomId(roomId);
        if (room == null) {
            throw new IllegalArgumentException("Room not found!!");
        }

        List<Message> messages = room.getMessages();
        int start = Math.max(0, messages.size() - (page + 1) * size);
        int end = Math.min(messages.size(), start + size);
        return messages.subList(start, end);
    }
}
