package com.daesoo.war3api.user.model.dto;

import com.daesoo.war3api.user.model.entity.User;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Builder
@Data
public class UserDto {

    private String email;

    private String siteNick;

    private String role;
    private String expireTime;
    private String gameNick;

    public static UserDto of(User user) {
        return UserDto.builder()
                .email(user.getEmail())
                .siteNick(user.getSiteNick())
                .role(user.getRole())
                .gameNick(user.getSiteNick())
                .build();
    }
}
