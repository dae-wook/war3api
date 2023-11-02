package com.daesoo.war3api.chat.entity;

import com.daesoo.war3api.chat.dto.ChatRoomRequestDto;
import com.daesoo.war3api.entity.Timestamp;
import com.daesoo.war3api.user.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatRoom extends Timestamp {
    public enum Status {
        READY, PLAYING
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String host;

    private String gameType;

    private String status;

    private int capacity;
    private int memberCount;
    private Boolean isNeedHelper;
    private Boolean isAllowBeginner;
    @OneToOne
    private RoomType roomType;


    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "chatRoom")
    private List<ChatUser> chatUsers = new ArrayList<>();


    public static ChatRoom create(ChatRoomRequestDto chatRoomRequestDto, RoomType roomType, User user) {
        return ChatRoom.builder()
                .title(chatRoomRequestDto.getTitle())
                .host(user.getSiteNick())
                .gameType(chatRoomRequestDto.getGameType())
                .status(Status.READY.toString())
                .capacity(chatRoomRequestDto.getCapacity())
                .isNeedHelper(chatRoomRequestDto.getIsNeedHelper())
                .memberCount(0)
                .roomType(roomType)
                .isAllowBeginner(chatRoomRequestDto.getIsAllowBeginner())
                .build();
    }

    public void updateChatRoom(ChatRoomRequestDto chatRoomRequestDto, RoomType roomType) {
        this.host = chatRoomRequestDto.getHost();
        this.title = chatRoomRequestDto.getTitle();
        this.status = chatRoomRequestDto.getStatus();
        this.capacity = chatRoomRequestDto.getCapacity();
        this.roomType = roomType;
        this.isNeedHelper = chatRoomRequestDto.getIsNeedHelper();
        this.isAllowBeginner = chatRoomRequestDto.getIsAllowBeginner();
    }

    public void enterUser() {
        this.memberCount++;
    }

    public void escapeUser() {
        this.memberCount--;
    }

    public void updateHost(ChatUser chatUser) {
        this.host = chatUser.getUser().getSiteNick();
    }

    public void initChatUsers(ChatUser chatUser) {
        List<ChatUser> chatUserList = new ArrayList<>();
        chatUserList.add(chatUser);
        this.chatUsers = chatUserList;
    }

}
