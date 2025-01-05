package com.pradosh.Chat.repositories;

import com.pradosh.Chat.entities.Room;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends MongoRepository<Room,String> {
    //get room using room id
    Room findByRoomId(String roomId);
}
