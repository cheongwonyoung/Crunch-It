package com.kb.crunchit.service;

import com.kb.crunchit.dto.ChatRoom;
import com.kb.crunchit.mapper.ChatRoomMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ChatRoomService {

    private final ChatRoomMapper chatRoomMapper;

    public List<ChatRoom> getAllChatRooms() {
        return chatRoomMapper.getChatRooms();
    }
}