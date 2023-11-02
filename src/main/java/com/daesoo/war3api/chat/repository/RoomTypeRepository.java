package com.daesoo.war3api.chat.repository;

import com.daesoo.war3api.chat.entity.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomTypeRepository extends JpaRepository<RoomType, Long> {
}
