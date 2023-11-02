package com.daesoo.war3api.chat.dto;

import com.daesoo.war3api.chat.entity.ChatRoom;
import com.daesoo.war3api.chat.entity.ChatUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ChatRoomResponseDto {

    private Long id;
    private String title;
    private String host;
    private String status;
    private int capacity;
    private int memberCount;
    private String regDt;
    private String modDt;
    private Boolean isNeedHelper;
    private Boolean isAllowBeginner;
    private RoomTypeResponseDto roomType;
    private List<ChatUserResponseDto> members = new ArrayList<>();

    public static ChatRoomResponseDto of(ChatRoom chatRoom) {
        return ChatRoomResponseDto.builder()
                .id(chatRoom.getId())
                .title(chatRoom.getTitle())
                .host(chatRoom.getHost())
                .status(chatRoom.getStatus())
                .capacity(chatRoom.getCapacity())
                .roomType(RoomTypeResponseDto.of(chatRoom.getRoomType()))
                .regDt(chatRoom.getRegDt().toString())
                .isNeedHelper(chatRoom.getIsNeedHelper())
                .isAllowBeginner(chatRoom.getIsAllowBeginner())
                .memberCount(chatRoom.getMemberCount())
                .members(chatRoom.getChatUsers() != null ? chatRoom.getChatUsers().stream().map(ChatUserResponseDto::of).collect(Collectors.toList()) : null)
                .build();
    }

}
