package com.daesoo.war3api.oner.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Entity;
import javax.persistence.Id;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Nickname {

    @Id
    private String nickName;

    private Boolean isValidUser = false;

    public void updateValid(boolean isValidUser) {
        this.isValidUser = isValidUser;
    }


}
