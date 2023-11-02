package com.daesoo.war3api.oner.character.service;

import com.daesoo.war3api.component.JwtProvider;
import com.daesoo.war3api.oner.character.model.Character;
import com.daesoo.war3api.oner.character.model.CharacterDto;
import com.daesoo.war3api.oner.character.model.ObjectData;
import com.daesoo.war3api.oner.material.entity.Material;
import com.daesoo.war3api.oner.material.repository.MaterialRepository;
import com.daesoo.war3api.oner.model.entity.*;
import com.daesoo.war3api.oner.model.repository.*;
import com.daesoo.war3api.user.model.entity.User;
import com.daesoo.war3api.user.repository.UserRepository;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.springframework.stereotype.Service;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Document;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ChracterServiceImpl implements ChracterService{

    private final SailorRepository sr;
    private final MaterialRepository mr;
    private final ColleagueRepository cr;
    private final OnerEquipmentRepository er;
    private final HeroRepository hr;
    private final ShipRepository shipRepo;
    private final PotionRepository potionRepository;
    private final NicknameRepository nicknameRepository;
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private final HttpClientUtil clientUtil;

    @Override
    public boolean isValid(String gameNick) {
        try {
            Document doc = Jsoup.connect("https://m16tool.xyz/Game/ONERPG/UserLog/LogResult?nicName=" + gameNick).get();
            Elements characters = doc.select("tbody > tr > td:nth-child(2)");//body > div > div > div.col-lg-9 > main > div > table > tbody > tr:nth-child(1) > td:nth-child(2)

            return characters.size() >= 1;
        }catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    public ArrayList<Character> getLimitedCoin(String nickName, HttpServletRequest request) {

        String token = request!=null?request.getHeader("Authorization"):null;
        User user = null;
        if(jwtProvider.checkJwt(token)) {
            user = userRepository.findBySiteNick(jwtProvider.siteNick(token)).orElseThrow(() -> new NullPointerException("doe"));
        }
        Gson gson = new Gson();
        List<Material> materials = mr.findAll();
        List<Sailor> sailors = sr.findAll();
        List<Colleague> colleagues = cr.findAll();
        List<OnerEquipment> equipmentList = er.findAll();
        List<Hero> heroList = hr.findAll();
        List<Ship> ships = shipRepo.findAll();
        List<Potion> potions = potionRepository.findAll();
        List<Item> items = new ArrayList<>();
        items.addAll(materials);
        items.addAll(sailors);
        items.addAll(colleagues);
        items.addAll(equipmentList);
        items.addAll(heroList);
        items.addAll(ships);
        items.addAll(potions);
        try {
            String decodedNickname = URLDecoder.decode(nickName, "UTF-8");
            ArrayList<Character> characterList = new ArrayList<>();
            List<ObjectData> characters = clientUtil.sendPostRequest(decodedNickname);

            if(characters.size() < 1) {
                nicknameRepository.deleteById(nickName);
                throw new NullPointerException("캐릭터가 존재하지 않습니다.");
            }
            for(int i = 0; i < characters.size(); i++) {
//                CharacterDto dto = gson.fromJson("{" + characters.get(i).text() + "}", CharacterDto.class);
                CharacterDto dto = gson.fromJson(characters.get(i).getObjectCode(), CharacterDto.class);
                ArrayList<String> equipment = new ArrayList<>();
                ArrayList<String> sailor = new ArrayList<>();
                ArrayList<String> colleague = new ArrayList<>();
                ArrayList<String> point = new ArrayList<>();
                ArrayList<String> inventories = new ArrayList<>();
                String ship = "";
                String hero = "";

                ArrayList<String> dtoEquipments = dto.getEquipments();
                for (int j = 0; j < dtoEquipments.size(); j++) {
                    String[] item = dtoEquipments.get(j).split(",");
                    for (int k = 0; k < items.size(); k++) {
                        if(item[0].equals(items.get(k).getItemNumber())) {
//                            equipment.add(items.get(k).getName() + ": " + item[1]);
                            equipment.add(items.get(k).getId() + ": " + item[1]);
                            break;
                        }else if(item[0].equals("0")){
                            equipment.add("빈공간");
                            break;
                        }
                    }
                }

                ArrayList<String> dtoSailors = dto.getSailors();
                for (int j = 0; j < dtoSailors.size(); j++) {
                    String[] item = dtoSailors.get(j).split(",");
                    for (int k = 0; k < sailors.size(); k++) {
                        if(item[0].equals(sailors.get(k).getItemNumber())) {
//                            sailor.add(sailors.get(k).getName() + ": " + item[1]);
                            sailor.add(sailors.get(k).getId() + ": " + item[1]);
                            break;
                        }else if(item[0].equals("0")){
                            sailor.add("빈공간");
                            break;
                        }
                    }
                }

                if(user != null && user.getRole().equals("STAFF")) {
                    ArrayList<String> dtoInventories = dto.getInventories();
                    for (int j = 0; j < dtoInventories.size(); j++) {
                        String[] item = dtoInventories.get(j).split(",");
                        for (int k = 0; k < items.size(); k++) {
                            if(item[0].equals(items.get(k).getItemNumber())) {
//                            sailor.add(sailors.get(k).getName() + ": " + item[1]);
                                inventories.add(items.get(k).getId() + ": " + item[1]);
                                break;
                            }else if(item[0].equals("0")){
                                inventories.add("빈공간");
                                break;
                            }else if(k == items.size() - 1) {
                                inventories.add(item[0] + ": " + item[1]);
                                break;
                            }
                        }
                    }
                }

                ArrayList<String> dtoColleagues = dto.getColleagues();
                for (int j = 0; j < dtoColleagues.size(); j++) {
                    String[] item = dtoColleagues.get(j).split(",");
                    for (int k = 0; k < colleagues.size(); k++) {
                        if(item[0].equals(colleagues.get(k).getItemNumber())) {
//                            colleague.add(colleagues.get(k).getName() + ": " + item[1]);
                            colleague.add(colleagues.get(k).getId() + ": " + item[1]);
                            break;
                        }else if(item[0].equals("0")){
                            colleague.add("빈공간");
                            break;
                        }
                    }
                }

                String[] shipSlot = dto.getShip().split(",");
                for (int k = 0; k < ships.size(); k++) {
                    if(shipSlot[0].equals(ships.get(k).getItemNumber())) {
//                        ship = ships.get(k).getName() + ": " + shipSlot[1];
                        ship = ships.get(k).getId() + ": " + shipSlot[1];
                        break;
                    }else if(shipSlot[0].equals("0")){
                        ship = "빈공간";
                        break;
                    }
                }

                String heroId = dto.getHeroId();
                int heroIndex = 0;
                for (int j = 0; j < heroList.size(); j++) {
                    if(heroId.equals(heroList.get(j).getId())) {
                        heroIndex = j;
                        break;
                    }
                }

                for (int k = 0; k < heroList.size(); k++) {
                    if(heroId.equals(heroList.get(k).getId())) {
                        heroId = heroList.get(k).getName();
//                        heroId = heroList.get(k).getId();
                        break;
                    }
                }



                characterList.add(new Character(i, heroId, heroList.get(heroIndex), dto.getBounty() + "000", ship, dto.getExp(), "0", dto.getLevel(), dto.getPoints(), equipment, sailor, colleague, inventories,dto.getStat(),dto.getEnd(), characters.get(i).getUpdateDate()));

            }

            return characterList;



        }catch (IOException e) {
            e.printStackTrace();
        }catch (JSONException e) {
            e.printStackTrace();
        }

//        return limetedCoin;
        return null;
    }


    public static int calc(String exp) {
        if(exp.length()<1){
            return 1;
        }
        int expp = Integer.parseInt(exp.trim());
        int level = 1;
        int i = 0;
        int reqExp = 3000;
        level++;
        while(expp > reqExp) {
            expp = expp-reqExp;
            level++;
            reqExp += 1000;
        }

        return level;
    }
}
