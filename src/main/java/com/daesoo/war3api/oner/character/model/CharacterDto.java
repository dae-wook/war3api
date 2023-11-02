package com.daesoo.war3api.oner.character.model;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;

@Getter
@ToString
public class CharacterDto {

    @SerializedName("HeroId")
    private String heroId;
    @SerializedName("Exp")
    private int exp;
    @SerializedName("STA_Point")
    private int level;
    @SerializedName("CRA1_Clear")
    private double crack;
    @SerializedName("CRA2_Clear")
    private double bigCrack;
    @SerializedName("Demon_Clear")
    private double demon;
    @SerializedName("EPSD_Clear")
    private double episode;
    @SerializedName("DS_LV")
    private double dangerLevel;
    @SerializedName("DF_LV")
    private double busterCall;
    @SerializedName("Ryuo")
    private int raily;
    @SerializedName("QstSkiCool")
    private double skillSpeed;
    @SerializedName("QstHP")
    private double hp;
    @SerializedName("Power_DMG") //피해증폭
    private double powerDmg;
    @SerializedName("Power_PG") //파괴증폭
    private double powerPg;
    @SerializedName("END_ST")
    private int end;
    @SerializedName("RCT_LV")
    private int rctLv;

    //Power_DMG 피해증폭
    //RCT_LV년 후
    //Power_PG 파괴증폭 입니다
    //DF_LV < 버스터 콜 디펜드 입니다!

    @SerializedName("Bounty")
    private int bounty;
    @SerializedName("Item0")
    private String equipment0;
    @SerializedName("Item1")
    private String equipment1;
    @SerializedName("Item2")
    private String equipment2;
    @SerializedName("Item3")
    private String equipment3;
    @SerializedName("Item4")
    private String equipment4;
    @SerializedName("Item5")
    private String equipment5;
    @SerializedName("Mount0")
    private String cosmeticItem0;
    @SerializedName("Mount1")
    private String cosmeticItem1;
    @SerializedName("Mount2")
    private String cosmeticItem2;
    @SerializedName("Mount3")
    private String cosmeticItem3;
    @SerializedName("Mount4")
    private String cosmeticItem4;
    @SerializedName("Mount5")
    private String cosmeticItem5;
    @SerializedName("Mount6")
    private String bike;
    @SerializedName("Mount7")
    private String pet;
    @SerializedName("Mount8")
    private String ship;
    @SerializedName("Mount9")
    private String colleague0;
    @SerializedName("Mount10")
    private String colleague1;
    @SerializedName("Mount11")
    private String colleague2;
    @SerializedName("Mount12")
    private String sailor0;
    @SerializedName("Mount13")
    private String sailor1;
    @SerializedName("Mount14")
    private String sailor2;
    @SerializedName("Mount15")
    private String sailor3;
    @SerializedName("Mount16")
    private String sailor4;
    @SerializedName("Mount17")
    private String sailor5;
    @SerializedName("Inv0")
    private String inv0;
    @SerializedName("Inv1")
    private String inv1;
    @SerializedName("Inv2")
    private String inv2;
    @SerializedName("Inv3")
    private String inv3;
    @SerializedName("Inv4")
    private String inv4;
    @SerializedName("Inv5")
    private String inv5;
    @SerializedName("Inv6")
    private String inv6;
    @SerializedName("Inv7")
    private String inv7;
    @SerializedName("Inv8")
    private String inv8;
    @SerializedName("Inv9")
    private String inv9;
    @SerializedName("Inv10")
    private String inv10;
    @SerializedName("Inv11")
    private String inv11;
    @SerializedName("Inv12")
    private String inv12;
    @SerializedName("Inv13")
    private String inv13;
    @SerializedName("Inv14")
    private String inv14;
    @SerializedName("Inv15")
    private String inv15;
    @SerializedName("Inv16")
    private String inv16;
    @SerializedName("Inv17")
    private String inv17;
    @SerializedName("Inv18")
    private String inv18;
    @SerializedName("Inv19")
    private String inv19;
    @SerializedName("Inv20")
    private String inv20;
    @SerializedName("Inv21")
    private String inv21;
    @SerializedName("Inv22")
    private String inv22;
    @SerializedName("Inv23")
    private String inv23;
    @SerializedName("Inv24")
    private String inv24;
    @SerializedName("Inv25")
    private String inv25;
    @SerializedName("Inv26")
    private String inv26;
    @SerializedName("Inv27")
    private String inv27;
    @SerializedName("Inv28")
    private String inv28;
    @SerializedName("Inv29")
    private String inv29;
    @SerializedName("Inv30")
    private String inv30;
    @SerializedName("Inv31")
    private String inv31;
    @SerializedName("Inv32")
    private String inv32;
    @SerializedName("Inv33")
    private String inv33;
    @SerializedName("Inv34")
    private String inv34;
    @SerializedName("Inv35")
    private String inv35;
    @SerializedName("Inv36")
    private String inv36;
    @SerializedName("Inv37")
    private String inv37;
    @SerializedName("Inv38")
    private String inv38;
    @SerializedName("Inv39")
    private String inv39;
    @SerializedName("Inv40")
    private String inv40;
    @SerializedName("Inv41")
    private String inv41;
    @SerializedName("Inv42")
    private String inv42;
    @SerializedName("Inv43")
    private String inv43;
    @SerializedName("Inv44")
    private String inv44;
    @SerializedName("Inv45")
    private String inv45;
    @SerializedName("Inv46")
    private String inv46;
    @SerializedName("Inv47")
    private String inv47;
    @SerializedName("Inv48")
    private String inv48;
    @SerializedName("Inv49")
    private String inv49;

    public ArrayList<String> getItems() {
        ArrayList<String> items = new ArrayList<>();
        items.add(equipment0);
        items.add(equipment1);
        items.add(equipment2);
        items.add(equipment3);
        items.add(equipment4);
        items.add(equipment5);
        items.add(ship);
        items.add(colleague0);
        items.add(colleague1);
        items.add(colleague2);
        items.add(sailor0);
        items.add(sailor1);
        items.add(sailor2);
        items.add(sailor3);
        items.add(sailor4);
        items.add(sailor5);

        return items;
    }

    public ArrayList<String> getEquipments() {
        ArrayList<String> items = new ArrayList<>();
        items.add(equipment0);
        items.add(equipment1);
        items.add(equipment2);
        items.add(equipment3);
        items.add(equipment4);
        items.add(equipment5);

        return items;
    }

    public ArrayList<String> getPoints() {
        ArrayList<String> items = new ArrayList<>();
        items.add("균열 숙련도: " + crack);
        items.add("대균열 숙련도: " + bigCrack);
        items.add("악마의 열매 숙련도: " + demon);
        items.add("에피소드 숙련도: " + episode);
        items.add("위험도: " + (int) Math.floor(dangerLevel));
        items.add("레일리: " + (raily + 1));
        items.add("성장: " + rctLv);
        items.add("버스터 콜 디펜드: " + busterCall);

        return items;
    }

    public ArrayList<String> getSailors() {
        ArrayList<String> items = new ArrayList<>();
        items.add(sailor0);
        items.add(sailor1);
        items.add(sailor2);
        items.add(sailor3);
        items.add(sailor4);
        items.add(sailor5);

        return items;
    }

    public ArrayList<String> getColleagues() {
        ArrayList<String> items = new ArrayList<>();
        items.add(colleague0);
        items.add(colleague1);
        items.add(colleague2);

        return items;
    }

    public ArrayList<String> getStat() {
        ArrayList<String> items = new ArrayList<>();
        items.add("ss: " + (int)(skillSpeed * 100));
        items.add("hp: " + (int)hp);
        items.add("powerDmg: " + Math.round((powerDmg * 100) * 100.0) / 100.0);
        items.add("powerPg: " + Math.round((powerPg * 100) * 100.0) / 100.0);

        return items;
    }


    public String getShip() {

        return ship;
    }

    public int getLevel() {

        return level + 1;
    }

    public ArrayList<String> getInventories() {
        ArrayList<String> items = new ArrayList<>();
        items.add(inv0);
        items.add(inv1);
        items.add(inv2);
        items.add(inv3);
        items.add(inv4);
        items.add(inv5);
        items.add(inv6);
        items.add(inv7);
        items.add(inv8);
        items.add(inv9);
        items.add(inv10);
        items.add(inv11);
        items.add(inv12);
        items.add(inv13);
        items.add(inv14);
        items.add(inv15);
        items.add(inv16);
        items.add(inv17);
        items.add(inv18);
        items.add(inv19);
        items.add(inv20);
        items.add(inv21);
        items.add(inv22);
        items.add(inv23);
        items.add(inv24);
        items.add(inv25);
        items.add(inv26);
        items.add(inv27);
        items.add(inv28);
        items.add(inv29);
        items.add(inv30);
        items.add(inv31);
        items.add(inv32);
        items.add(inv33);
        items.add(inv34);
        items.add(inv35);
        items.add(inv36);
        items.add(inv37);
        items.add(inv38);
        items.add(inv39);
        items.add(inv40);
        items.add(inv41);
        items.add(inv42);
        items.add(inv43);
        items.add(inv44);
        items.add(inv45);
        items.add(inv46);
        items.add(inv47);
        items.add(inv48);
        items.add(inv49);
        return items;
    }


}
