package com.daesoo.war3api.oner.character.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class LimitedCoin {

    private String bounty;
    private String exp;
    private String ryuo;
    private int lv;
    private ArrayList<String> points;
    private ArrayList<String> equipments;
    private ArrayList<String> sailors;
    private ArrayList<String> colleagues;
    private ArrayList<String> inventories;
    private String saveDate;

}
