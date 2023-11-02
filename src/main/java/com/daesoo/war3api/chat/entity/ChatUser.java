package com.daesoo.war3api.chat.entity;

import com.daesoo.war3api.chat.dto.ChatUserRequestDto;
import com.daesoo.war3api.user.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatUser {

    public enum Status {
        WAIT, READY, AFK
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String status;
    @ManyToOne
    @JoinColumn(unique = true)
    private User user;

    @ManyToOne
    private ChatRoom chatRoom;
    private String peerId;


    public static ChatUser create(User user, ChatUserRequestDto dto, ChatRoom chatRoom) {
        return ChatUser.builder()
                .user(user)
                .status(Status.READY.toString())
                .chatRoom(chatRoom)
                .peerId(dto.getPeerId())
                .build();
    }

    public void update(ChatUserRequestDto dto) {
        this.peerId = dto.getPeerId();
    }

}
