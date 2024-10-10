package com.kb.crunchit.mapper;

import com.kb.crunchit.dto.ChatRoom;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChatRoomMapper {
    void insertChatRoom(String name);
    List<ChatRoom> getChatRooms();
}
