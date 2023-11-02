package com.daesoo.war3api.oner.material.entity;

import com.daesoo.war3api.oner.model.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Material extends Item {

//    @Id
//    private String id;//인게임 코드
//
//    private String name;
    private String dropMonster;
    private String description;
    private boolean imageYn;

}
