package com.daesoo.war3api.chat.dto;

import com.daesoo.war3api.chat.entity.ChatUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ChatUserResponseDto {

    private Long id;
    private String nickname;
    private String status;
    private String peerId;

    public static ChatUserResponseDto of(ChatUser chatUser) {
        return ChatUserResponseDto.builder()
                .id(chatUser.getId())
                .nickname(chatUser.getUser().getSiteNick())
                .status(chatUser.getStatus())
                .peerId(chatUser.getPeerId())
                .build();
    }

}
