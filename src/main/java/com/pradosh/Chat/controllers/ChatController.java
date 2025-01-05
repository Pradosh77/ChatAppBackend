package com.pradosh.Chat.controllers;


import com.pradosh.Chat.entities.Message;
import com.pradosh.Chat.entities.Room;
import com.pradosh.Chat.payloads.MessageRequest;
import com.pradosh.Chat.repositories.RoomRepository;
import com.pradosh.Chat.services.RoomService;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@CrossOrigin("*")
public class ChatController {

    private RoomService roomService;

    public ChatController(RoomService roomService) {
        this.roomService = roomService;
    }


    @MessageMapping("/sendMessage/{roomId}")// /app/sendMessage/roomId
    @SendTo("/topic/room/{roomId}")
    public Message sendMessage(
            @DestinationVariable String roomId,
            @RequestBody MessageRequest messageRequest
    ){

        Room room = roomService.findRoomByRoomId(roomId);

        Message message = new Message(messageRequest.getSender(),messageRequest.getContent());

        if(room != null){
            room.getMessages().add(message);
            roomService.save(room);
        }
        else {
            throw new RuntimeException("room not found !!");
        }
        return message;
    }
}
