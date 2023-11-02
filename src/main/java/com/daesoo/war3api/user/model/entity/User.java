package com.daesoo.war3api.user.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class User {

    @Id
    private String email;

    private String siteNick;

    @Column(columnDefinition = "varchar(20) default 'USER'")
    private String role;

    private LocalDateTime joinLimit;

    private LocalDateTime regDt;
    private String gameNick;

    public void updateGameNick(String gameNick) {
        this.gameNick = gameNick;
    }


}
