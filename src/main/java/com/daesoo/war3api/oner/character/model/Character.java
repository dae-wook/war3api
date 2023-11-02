package com.daesoo.war3api.oner.character.model;

import com.daesoo.war3api.oner.model.entity.Hero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Character {

    private int index;
    private String heroName;
    private Hero hero;
    private String bounty;
    private String ship;
    private int exp;
    private String ryuo;
    private int lv;
    private ArrayList<String> information;
    private ArrayList<String> equipment;
    private ArrayList<String> sailor;
    private ArrayList<String> colleague;
    private ArrayList<String> inventory;
    private ArrayList<String> stat;
    private int end;
    private String saveDate;

    public static Character getMainCharacter(List<Character> list) {

        int index = 0;
        if(list.size() > 0) {
            int max = 0;
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getExp() > 0 && list.get(i).getBounty().length() > 0) {
                    if (list.get(i).getExp() > max) {
                        max = list.get(i).getExp();
                        index = i;
                    }
                }
            }
        }
        return list.get(index);
    }

    public int getIntBounty() {
        return Integer.parseInt(this.bounty.trim());
    }

}
