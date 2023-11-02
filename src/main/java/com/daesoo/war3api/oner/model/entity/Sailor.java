package com.daesoo.war3api.oner.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Sailor extends Item{

//    @Id
//    private String id;//인게임 코드
//
//    private String name;
    private String ingredients;
    private String dropMonster;
    private String grade;
    private String option;
    private String type;

    public void updateImageName(Sailor sailor) {
        this.setImageName(sailor.getId());
    }


}
