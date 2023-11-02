package com.daesoo.war3api.chat.controller;

import com.daesoo.war3api.chat.dto.*;
import com.daesoo.war3api.chat.service.ChatRoomService;
import com.daesoo.war3api.dto.ResponseDto;
//import com.daesoo.war3api.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat-rooms")
@Slf4j
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @GetMapping
    public ResponseDto<List<ChatRoomResponseDto>> getRoomList(@RequestParam int page, @RequestParam int size, @RequestParam(required = false, defaultValue = "999") Long roomTypeId) {
        return ResponseDto.success(chatRoomService.getRoomList(page-1, size, roomTypeId));
    }

    @GetMapping("/{roomId}")
    public ResponseDto<ChatRoomResponseDto> getRoom(@PathVariable Long roomId) {
        return ResponseDto.success(chatRoomService.getRoom(roomId));
    }
    @GetMapping("/room-types")
    public ResponseDto<List<RoomTypeResponseDto>> getRoomTypeList() {
        return ResponseDto.success(chatRoomService.getRoomTypeList());
    }

    @GetMapping("/members/{siteNick}")
    public ResponseDto<Long> getUserChatRoom(@PathVariable String siteNick) {
        return ResponseDto.success(chatRoomService.getUserChatRoom(siteNick));
    }
    @GetMapping("/members")
    public ResponseDto<List<ChatUserResponseDto>> getChatUsers() {
        return ResponseDto.success(chatRoomService.getChatUsers());
    }


    @PostMapping
    public ResponseDto<ChatRoomResponseDto> createRoom(@RequestBody ChatRoomRequestDto chatRoomRequestDto, HttpServletRequest request) {

        return ResponseDto.success(chatRoomService.createRoom(chatRoomRequestDto, request));
    }

    @PostMapping("/{roomId}/members")
    public ResponseDto<ChatUserResponseDto> createChatUser(@PathVariable Long roomId,@RequestBody ChatUserRequestDto dto, HttpServletRequest request) {
        return ResponseDto.success(chatRoomService.createChatUser(roomId, dto, request));
    }

    @DeleteMapping("/{roomId}/{siteNick}")
    public ResponseDto<ChatUserResponseDto> deleteChatRoomChatUser(@PathVariable Long roomId, @PathVariable String siteNick) {
        return ResponseDto.success(chatRoomService.deleteChatRoomChatUser(roomId, siteNick));
    }

    @DeleteMapping("/members/{siteNick}")
    public ResponseDto<ChatUserResponseDto> deleteChatUser(@PathVariable String siteNick) {
        return ResponseDto.success(chatRoomService.deleteChatUser(siteNick));
    }

    @DeleteMapping("/{roomId}")
    public ResponseDto<String> deleteChatRoom(@PathVariable Long roomId) {
        return ResponseDto.success(chatRoomService.deleteChatRoom(roomId));
    }

    @PutMapping("/members/{siteNick}")
    public ResponseDto<ChatUserResponseDto> updatePeerId(@PathVariable String siteNick,@RequestBody ChatUserRequestDto dto) {
        return ResponseDto.success(chatRoomService.updatePeerId(siteNick, dto));
    }

    @PutMapping("/{roomId}")
    public ResponseDto<ChatRoomResponseDto> updateChatRoom(@PathVariable Long roomId, @RequestBody ChatRoomRequestDto chatRoomRequestDto, HttpServletRequest request) {
        return ResponseDto.success(chatRoomService.updateChatRoom(roomId, chatRoomRequestDto, request));
    }


}
