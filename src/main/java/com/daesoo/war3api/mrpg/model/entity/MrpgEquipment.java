package com.daesoo.war3api.mrpg.model.entity;

import com.daesoo.war3api.mrpg.model.dto.MrpgEquipmentDto;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@ToString
public class MrpgEquipment extends MrpgItem{


    private String ingredients;
    private String type;
    private String option;
    private String dedicatedOption;
    private String passive;
    private String active;
    private String grade;

    private int level;

    public MrpgEquipment(MrpgEquipmentDto mrpgEquipmentDto) {
        this.setName(mrpgEquipmentDto.getName());
        this.option = mrpgEquipmentDto.getOption();
        this.type = mrpgEquipmentDto.getType();
        this.grade = mrpgEquipmentDto.getGrade();
        this.level = mrpgEquipmentDto.getLevel();
        this.ingredients = mrpgEquipmentDto.getIngredients();
        this.active = mrpgEquipmentDto.getActive();
        this.passive = mrpgEquipmentDto.getPassive();
        this.dedicatedOption = mrpgEquipmentDto.getDedicatedOption();
        setDropMonster(mrpgEquipmentDto.getDropMonster());
//        if(mrpgEquipmentDto.getIngredients().equals("")){
//            this.ingredients = null;
//        }else {
//            this.ingredients = mrpgEquipmentDto.getIngredients();
//        }
//        if(mrpgEquipmentDto.getActive().equals("")) {
//            this.active = null;
//        }else {
//            this .active = mrpgEquipmentDto.getActive();
//        }
//        if(mrpgEquipmentDto.getPassive().equals("")) {
//            this.passive = null;
//        }else {
//            this.passive = mrpgEquipmentDto.getPassive();
//        }
//        if(mrpgEquipmentDto.getDedicatedOption().equals("")) {
//            this.dedicatedOption = null;
//        }else {
//            this.dedicatedOption = mrpgEquipmentDto.getDedicatedOption();
//        }
//        if(mrpgEquipmentDto.getDropMonster().equals("")) {
//            this.setDropMonster(null);
//        }else {
//            this.setDropMonster(mrpgEquipmentDto.getDropMonster());
//        }

    }



}
