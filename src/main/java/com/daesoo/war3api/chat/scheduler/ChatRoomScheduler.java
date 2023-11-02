package com.daesoo.war3api.chat.scheduler;

import com.daesoo.war3api.chat.entity.ChatRoom;
import com.daesoo.war3api.chat.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ChatRoomScheduler {

    private final ChatRoomRepository chatRoomRepository;

    @Scheduled(cron = "0 0 * * * ?") // 매 정각에 실행
//    @Scheduled(cron = "0 0/1 * * * *")
    public void confirmMemberCountAndDeleteChatRoom() {
        List<ChatRoom> chatRooms = chatRoomRepository.findAllByChatUsersIsNull();
        if(chatRooms.size() <= 0) return;
        chatRoomRepository.deleteAll(chatRooms);
    }

}
