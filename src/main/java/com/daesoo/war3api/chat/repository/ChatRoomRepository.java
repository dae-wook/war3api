package com.daesoo.war3api.chat.repository;

import com.daesoo.war3api.chat.entity.ChatRoom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    Page<ChatRoom> findAllByRoomTypeIdAndChatUsersIsNotNull(Long roomTypeId, Pageable pageable);
    Page<ChatRoom> findAllByChatUsersIsNotNull(Pageable pageable);
    List<ChatRoom> findAllByChatUsersIsNull();

    List<ChatRoom> findAllByMemberCount(int count);
}
