package com.daesoo.war3api.oner.model.entity;

import lombok.*;

import javax.persistence.Entity;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class OnerEquipment extends Item {
    private String grade;

    private String option;

    private String gradeOption;

    private String dropMonster;
    private String type;

    public void updateImageName(OnerEquipment e) {
        this.setImageName("e"+e.getId());
    }
}
