package com.daesoo.war3api.oner.model.entity;


import lombok.Data;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@Data
@MappedSuperclass
public class Item {

    @Id
    private String id;//인게임 코드

    private String name;

    private String imageName;

    private String itemNumber;
}