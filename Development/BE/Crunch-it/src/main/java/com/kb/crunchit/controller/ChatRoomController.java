package com.kb.crunchit.controller;

import com.kb.crunchit.dto.ChatRoom;
import com.kb.crunchit.service.ChatRoomService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/chatrooms")
@AllArgsConstructor
public class ChatRoomController {
    private final ChatRoomService chatRoomService;

    @GetMapping
    public ResponseEntity<?> getAllChatRooms() {
        System.out.println("in");
        List<ChatRoom> allChatRooms = chatRoomService.getAllChatRooms();
        System.out.println(allChatRooms);
        return ResponseEntity.ok().body(allChatRooms);
    }

    @PostMapping
    public ResponseEntity<?> createChatRoom(@RequestBody Map<String, String> params) {
        chatRoomService.createChatRoom(params.get("name"));

        return ResponseEntity.ok().body("ok");
    }
}