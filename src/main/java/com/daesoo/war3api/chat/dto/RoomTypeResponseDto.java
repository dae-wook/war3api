package com.daesoo.war3api.chat.dto;

import com.daesoo.war3api.chat.entity.RoomType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RoomTypeResponseDto {

    private Long id;
    private String name;

    public static RoomTypeResponseDto of(RoomType roomType) {
        return RoomTypeResponseDto.builder()
                .id(roomType.getId())
                .name(roomType.getName())
                .build();
    }
}
