package com.daesoo.war3api.oner.service;

import com.daesoo.war3api.oner.character.model.Character;
import com.daesoo.war3api.oner.material.entity.Material;
import com.daesoo.war3api.oner.model.dto.RankingResponseDto;
import com.daesoo.war3api.oner.model.entity.*;

import java.util.List;

public interface ApiService {

    List<Sailor> getSailorList();
    List<Material> getMaterialList();
    List<Colleague> getColleagueList();
    List<OnerEquipment> getEquipmentList();
    List<Hero> getHeroList();
    List<Nickname> getNickNameList();
    List<Ryuo> getRyuoList();
    List<Ship> getShipList();
    List<Potion> getPotionList();
    List<Status> getApiServerStatus();
    List<Synergy> getSynergyList();
    List<RankingResponseDto> getRankingList(int page, int size, String character);

    List<CharacterPageView> getCharacterPageViewList(String startDate, String endDate, int length);
    List<CompositionPageView> getCompositionPageViewList(String startDate, String endDate, int length);
//    boolean memberRegister(String userId, String password, String name);
    boolean murgeCharcterPageViewCount(String name, int pageView);
    boolean murgeCompositionPageViewCount(String name, int pageView);
    boolean characterViewRegister(String name);
    boolean compositionViewRegister(String name);
    boolean register(String nickName,String saveDate, String name, Hero hero, String exp, String bounty, String sailors, String colleagues,int lv, int end, String ship);
    boolean createNickname(String nickName, List<Character> list);
    boolean register(String itemCode, String itemName);

    boolean setImageName();
}
