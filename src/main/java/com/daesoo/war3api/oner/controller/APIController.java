package com.daesoo.war3api.oner.controller;

import com.daesoo.war3api.dto.ResponseDto;
import com.daesoo.war3api.oner.character.model.Character;
import com.daesoo.war3api.oner.character.service.ChracterService;
import com.daesoo.war3api.component.JwtProvider;
import com.daesoo.war3api.oner.material.entity.Material;
import com.daesoo.war3api.oner.model.dto.RankingResponseDto;
import com.daesoo.war3api.oner.model.entity.*;
import com.daesoo.war3api.oner.service.ApiService;
import com.google.gson.Gson;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

//@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Controller
//@RequestMapping("/oner/api")
@RequestMapping("/api")
public class APIController {

    private final ApiService as;
    private final ChracterService cs;
    private final Gson gson;

    private final JwtProvider jwt;

    @ResponseBody
    @GetMapping("/rawCharacterData")
    public ArrayList<Character> inventory(String nickName, HttpServletRequest request) {


        return cs.getLimitedCoin(nickName, request);
    }

    @ResponseBody
    @GetMapping("/sailors")
    public ResponseDto<List<Sailor>> sailors() {

        return ResponseDto.success(as.getSailorList());
    }

    @ResponseBody
    @GetMapping("/ships")
    public ResponseDto<List<Ship>> ships() {

        return ResponseDto.success(as.getShipList());
    }

    @ResponseBody
    @GetMapping("/colleagues")
    public ResponseDto<List<Colleague>> colleagues() {

        return ResponseDto.success(as.getColleagueList());
    }

    @ResponseBody
    @GetMapping("/equipments")
    public ResponseDto<List<OnerEquipment>> equipments() {

        return ResponseDto.success(as.getEquipmentList());
    }

    @ResponseBody
    @GetMapping("/heroes")
    public ResponseDto<List<Hero>> heroes() {

        return ResponseDto.success(as.getHeroList());
    }

    @ResponseBody
    @GetMapping("/etcItems")
    public ResponseDto<List<Material>> etcItems() {

        return ResponseDto.success(as.getMaterialList());
    }

    @ResponseBody
    @GetMapping("/synergies")
    public ResponseDto<List<Synergy>> synergyList() {

        return ResponseDto.success(as.getSynergyList());
    }

    @ResponseBody
    @GetMapping("/potions")
    public ResponseDto<List<Potion>> potionList() {

        return ResponseDto.success(as.getPotionList());
    }

    @ResponseBody
    @GetMapping("/gameUsers")
    public ResponseDto<List<Nickname>> gameUsers() {

        return ResponseDto.success(as.getNickNameList());
    }

    @ResponseBody
    @GetMapping("/ryuoList")
    public ResponseDto<List<Ryuo>> ryuoList() {

        return ResponseDto.success(as.getRyuoList());
    }

    @ResponseBody
    @GetMapping("/ranking")
    public ResponseDto<List<RankingResponseDto>> ranking(@ApiParam(value = "page", required = true, example = "1") @RequestParam("page") int page,
                                                         @ApiParam(value="size", required = true, example = "10") @RequestParam("size") int size,
                                                         @ApiParam(value="character", required = true, example = "all 또는 luffy") @RequestParam("character") String character) {

        return ResponseDto.success(as.getRankingList(page-1, size, character));
    }

    @ResponseBody
    @GetMapping("/characterPageViews")
    public ResponseDto<List<CharacterPageView>> characterPageViews(String startDate, String endDate, int length) {

        return ResponseDto.success(as.getCharacterPageViewList(startDate,endDate, length));
    }

    @ResponseBody
    @GetMapping("/compositionPageViews")
    public ResponseDto<List<CompositionPageView>> compositionPageViews(String startDate, String endDate , int length) {

        return ResponseDto.success(as.getCompositionPageViewList(startDate, endDate, length));
    }

    @ResponseBody
    @GetMapping("/items")
    public ResponseDto<List<Item>> allList() {

        Gson gson = new Gson();
        List<Material> materialList = as.getMaterialList();
        List<Sailor> sailorList = as.getSailorList();
        List<Colleague> colleagueList = as.getColleagueList();
        List<OnerEquipment> equipmentList = as.getEquipmentList();
        List<Ship> shipList = as.getShipList();
        List<Potion> potionList = as.getPotionList();
        List<Item> items = new ArrayList<>();
        items.addAll(materialList);
        items.addAll(sailorList);
        items.addAll(colleagueList);
        items.addAll(equipmentList);
        items.addAll(shipList);
        items.addAll(potionList);

        return ResponseDto.success(items);
    }

    @ResponseBody
    @GetMapping("/characterList")
    public ResponseDto<List<Character>> chracterList(String nickName, HttpServletRequest request) {

//        List<Character> list = cs.getCharacterList(nickName);
        List<Character> list = cs.getLimitedCoin(nickName, request);
        if(list.size() == 0) {
            return null;
        }
        if(nickName.equals("Sgod") || nickName.equals("eoeo0114") || nickName.equals("onerpg0001") || nickName.equals("INTERPOL") || nickName.equals("EEL")) {
            return null;
        }else {
            as.createNickname(nickName, list);
        }

        return ResponseDto.success(list);
    }

    @ResponseBody
    @GetMapping("/serverStatus")
    public String serverStatus() {

        List<Status> list = as.getApiServerStatus();

        return gson.toJson(list.get(0).getStatus());
    }


    @ResponseBody
    @PostMapping("/register/characterPageView")
    public ResponseDto<Boolean> registerCharacterPageView(String name) {

        return ResponseDto.success(as.characterViewRegister(name));
    }

    @ResponseBody
    @PostMapping("/register/compositionPageView")
    public ResponseDto<Boolean> compositionPageView(String name) {

        return ResponseDto.success(as.compositionViewRegister(name));
    }
//
//    @ResponseBody
//    @PostMapping("api/register/gameUser")
//    public boolean registerGameUser(String nickName) {
//
//        boolean result = as.register(nickName);
//
//        return result;
//    }

    @ResponseBody
    @PostMapping("/register/report")
    public ResponseDto<Boolean> registerLog(String code, String name) {

        return ResponseDto.success(as.register(code, name));
    }


    @ResponseBody
    @PostMapping("/register/murgeCharacterView")
    public ResponseDto<Boolean> murgeCharacterView(String name, int pageView) {

        return ResponseDto.success(as.murgeCharcterPageViewCount(name, pageView));
    }

    @ResponseBody
    @PostMapping("/register/murgeCompositionView")
    public ResponseDto<Boolean> murgeCompositionView(String name, int pageView) {

        return ResponseDto.success(as.murgeCompositionPageViewCount(name, pageView));
    }

    @ResponseBody
    @PostMapping("/setImageName")
    public ResponseDto<Boolean> setImageName() {

        return ResponseDto.success(as.setImageName());
    }




    @Scheduled(cron = "0 0 * * * *")
    @ResponseBody
    @GetMapping("/rankingUpdate")
    public void rankingUpdate() {


            List<Nickname> nickNameList = as.getNickNameList();
            for (Nickname v : nickNameList) {
                String nickName = v.getNickName();
                ArrayList<Character> list = new ArrayList<>();
                if(!v.getIsValidUser()) {
                    continue;
                }
                try{
                    list = cs.getLimitedCoin(nickName, null);
                }catch (NullPointerException e) {
                    as.register("존재하지 않는 캐릭터", v.getNickName());
                }
                if(list.size() > 0) {
                    Character mainCharacter = Character.getMainCharacter(list);
                    String sailors = mainCharacter.getSailor().toString();
                    String colleagues = mainCharacter.getColleague().toString();
                    String ship = mainCharacter.getShip();
                    Hero hero = mainCharacter.getHero();
                    try {
                        as.register(nickName,mainCharacter.getSaveDate(), mainCharacter.getHeroName().trim(), hero, mainCharacter.getExp() + "", mainCharacter.getBounty(), sailors, colleagues, mainCharacter.getLv(), mainCharacter.getEnd(), ship);
                    } catch (NumberFormatException e) {
                        as.register("랭킹 업데이트 에러", v.getNickName());
                    }
                }
            }


    }






    public static int calc(int exp) {
        int level = 1;
        int i = 0;
        int reqExp = 3000;
        level++;
        while(exp > reqExp) {
            exp = exp-reqExp;
            level++;
            reqExp += 1000;
        }

        return level;
    }


}
