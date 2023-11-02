package com.daesoo.war3api.chat.repository;

import com.daesoo.war3api.chat.entity.ChatRoom;
import com.daesoo.war3api.chat.entity.ChatUser;
import com.daesoo.war3api.user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatUserRepository extends JpaRepository<ChatUser, Long> {
    Optional<ChatUser> findByChatRoomAndUser(ChatRoom chatRoom, User user);

    Optional<ChatUser> findByUserEmail(String email);

    Optional<ChatUser> findByUserSiteNick(String siteNick);
}
