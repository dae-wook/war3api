package com.daesoo.war3api.oner.service;

import com.daesoo.war3api.oner.character.model.Character;
import com.daesoo.war3api.oner.material.entity.Material;
import com.daesoo.war3api.oner.material.repository.MaterialRepository;
import com.daesoo.war3api.oner.model.dto.BuildResponseDto;
import com.daesoo.war3api.oner.model.dto.RankingResponseDto;
import com.daesoo.war3api.oner.model.entity.*;
import com.daesoo.war3api.oner.model.repository.*;
import com.daesoo.war3api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ApiServiceImpl implements ApiService {

    private final UserRepository userRepository;
    private final MaterialRepository mr;
    private final SailorRepository sr;
    private final ColleagueRepository cr;
    private final OnerEquipmentRepository er;
    private final HeroRepository hr;
    private final PotionRepository pr;
    private final ShipRepository shr;
    private final ReportRepository rr;
    private final NicknameRepository nr;
    private final RyuoRepository ryuoRepository;
    private final StatusRepository star;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final CharacterPageViewRepository characterPageViewRepository;
    private final CompositionrPageViewRepository compositionPageViewRepository;
    private final RankingRepository rankingRepository;
    private final SynergyRepository synergyRepository;




    @Override
    public List getSailorList() {

        List<Sailor> list = sr.findAll();

        return list;
    }


    @Override
    public List getMaterialList() {

        List<Material> list = mr.findAll();

        return list;
    }

    public List<Colleague> getColleagueList() {

        List<Colleague> list = cr.findAll();

        return list;
    }

    public List<OnerEquipment> getEquipmentList() {

        List<OnerEquipment> list = er.findAll();

        return list;
    }

    public List<Hero> getHeroList() {

        List<Hero> list = hr.findAll();

        return list;
    }

    public List<Nickname> getNickNameList() {

        List<Nickname> list = nr.findAll();

        return list;
    }

    @Override
    public List<Ryuo> getRyuoList() {

        List<Ryuo> list = ryuoRepository.findAll();

        return list;
    }

    @Override
    public List<Ship> getShipList() {
        List<Ship> list = shr.findAll();

        return list;
    }

    @Override
    public List<Potion> getPotionList() {

        List<Potion> list = pr.findAll();

        return list;
    }

    @Override
    public List<Status> getApiServerStatus() {

        List<Status> list = star.findAll();

        return list;
    }

    @Override
    public List<Synergy> getSynergyList() {
        List<Synergy> list = synergyRepository.findAll();

        return list;
    }

    @Override
    public List<RankingResponseDto> getRankingList(int page, int size, String character) {
        Sort sort = Sort.by(Sort.Direction.DESC, "bounty", "saveDate");
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Ranking> rankings;
        List<RankingResponseDto> dtoList = new ArrayList<>();
        if (character.equals("all")) {
            rankings = rankingRepository.findAllByOrderByBountyDesc(pageable);
        } else {
            rankings = rankingRepository.findAllByHeroImageNameOrderByBountyDesc(character, pageable);
        }

        for(Ranking ranking : rankings) {
            dtoList.add(RankingResponseDto.of(ranking));
        }
        return dtoList;
    }


    @Override
    public List<CharacterPageView> getCharacterPageViewList(String startDate, String endDate, int length) {

        int[] startDt = new int[3];
        int[] endDt = {LocalDate.now().getYear(), LocalDate.now().getMonthValue() , LocalDate.now().getDayOfMonth()};

        for (int i = 0; i < 3; i++) {
            if(endDate != null) {
                startDt[i] = Integer.parseInt(startDate.split("-")[i]);
                endDt[i] = Integer.parseInt(endDate.split("-")[i]);
            }else {
                startDt[i] = Integer.parseInt(startDate.split("-")[i]);

            }
        }
        List<CharacterPageView> list = characterPageViewRepository.findAllByUpdateDtBetweenQuery(LocalDate.of(startDt[0],startDt[1],startDt[2]), LocalDate.of(endDt[0],endDt[1],endDt[2]));
        if(list.size() < 10) {
            return list;
        }

        return list.subList(0, length);
    }

    @Override
    public List<CompositionPageView> getCompositionPageViewList(String startDate, String endDate, int length) {

        int[] startDt = new int[3];
        int[] endDt = {LocalDate.now().getYear(), LocalDate.now().getMonthValue() , LocalDate.now().getDayOfMonth()};

        for (int i = 0; i < 3; i++) {
            if(endDate != null) {
                startDt[i] = Integer.parseInt(startDate.split("-")[i]);
                endDt[i] = Integer.parseInt(endDate.split("-")[i]);
            }else {
                startDt[i] = Integer.parseInt(startDate.split("-")[i]);
            }
        }
        List<CompositionPageView> list = compositionPageViewRepository.findAllByUpdateDtBetween(LocalDate.of(startDt[0],startDt[1],startDt[2]), LocalDate.of(endDt[0],endDt[1],endDt[2]));
        for(int i = 0 ; i < list.size(); i++) {
            for (int j = 0 ; j < list.size() ; j++) {
                try{

                    if(list.get(i).getName() != null) {
                        if (list.get(i).getName().equals(list.get(j).getName())) {
                            list.get(i).setPageView(list.get(i).getPageView() + list.get(j).getPageView());
                            list.remove(j);
                        }
                    }
                }catch (IndexOutOfBoundsException e) {
                }
            }
        }
        if(list.size() < 1) {
            throw new NullPointerException("조합법 조회수 랭킹을 찾을수 없음");
        }
        list.sort((o1, o2) -> o2.getPageView() - o1.getPageView());

        return list.subList(0, length);
    }

//    @Override
//    public boolean memberRegister(String userId, String password, String name) {
//
//        Optional<User> optionalMember = memberRepository.findById(userId);
//
//        if(optionalMember.isPresent()) {
//            return false;
//        }
//
//        User member = User.builder()
//                .userId(userId)
//                .password(password)
//                .name(name)
//                .regDt(LocalDate.now())
//                .role("USER")
//                .build();
//        System.out.println(member);
//        memberRepository.save(member);
//
//        return true;
//    }

    @Override
    public boolean murgeCharcterPageViewCount(String name, int pageView) {

        Optional<CharacterPageView> oc = characterPageViewRepository.findByName(name);
        if(oc.isPresent()) {
            CharacterPageView cpv = oc.get();
            cpv.setPageView(pageView);
            characterPageViewRepository.save(cpv);
            return true;
        }

        CharacterPageView characterPageView = CharacterPageView.builder()
                .name(name)
                .pageView(pageView)
                .updateDt(LocalDate.now())
                .build();
        characterPageViewRepository.save(characterPageView);


        return true;
    }

    @Override
    public boolean murgeCompositionPageViewCount(String name, int pageView) {

        Optional<CompositionPageView> oc = compositionPageViewRepository.findByName(name);
        if(oc.isPresent()) {
            CompositionPageView cpv = oc.get();
            cpv.setPageView(pageView);
            compositionPageViewRepository.save(cpv);
            return true;
        }

        CompositionPageView compositionPageView = CompositionPageView.builder()
                .name(name)
                .pageView(pageView)
                .updateDt(LocalDate.now())
                .build();
        compositionPageViewRepository.save(compositionPageView);
        return true;
    }

    @Override
    public boolean characterViewRegister(String name) {

        Optional<CharacterPageView> optionalCharacterPageView = characterPageViewRepository.findByNameAndUpdateDt(name, LocalDate.now());

        if(optionalCharacterPageView.isPresent()) {
            CharacterPageView characterPageView = optionalCharacterPageView.get();
            characterPageView.setPageView(characterPageView.getPageView() + 1);
            characterPageViewRepository.save(characterPageView);
            return true;
        }

        CharacterPageView characterPageView = CharacterPageView.builder()
                .name(name)
                .pageView(1)
                .updateDt(LocalDate.now())
                .build();
        characterPageViewRepository.save(characterPageView);



        return true;
    }

    @Override
    public boolean compositionViewRegister(String name) {

        Optional<CompositionPageView> optionalCharacterPageView = compositionPageViewRepository.findByNameAndUpdateDt(name, LocalDate.now());

        if(optionalCharacterPageView.isPresent()) {
            CompositionPageView compositionPageView = optionalCharacterPageView.get();
            compositionPageView.setPageView(compositionPageView.getPageView() + 1);
            compositionPageViewRepository.save(compositionPageView);

            return true;
        }

        CompositionPageView compositionPageView = CompositionPageView.builder()
                .name(name)
                .pageView(1)
                .updateDt(LocalDate.now())
                .build();
        compositionPageViewRepository.save(compositionPageView);



        return true;
    }

    @Override
    public boolean register(String nickName, String saveDate, String name,Hero hero, String exp, String bounty, String sailors, String colleagues, int lv, int end, String ship) {
        Optional<Ranking> optionalRanking = rankingRepository.findByNickNameAndName(nickName, name);

        List<String> banList = new ArrayList();
        banList.add("aergh");
        banList.add("gorkd47");
        banList.add("ehrtjdudhkd");
        banList.add("and2");
        banList.add("ragemonk");
        banList.add("Cho.D.J");
        banList.add("OOOO");
        banList.add("Clown_tia");
        for(String ban : banList) {
            if(nickName.equals(ban)) {
                return false;
            }
        }

        if (optionalRanking.isPresent()) {
            Ranking ranking = optionalRanking.get();
            ranking.setExp(Integer.parseInt(exp.trim()));
            ranking.setBounty(Integer.parseInt(bounty.trim()));
            ranking.setSailors(sailors);
            ranking.setColleagues(colleagues);
            ranking.setSaveDate(saveDate);
            ranking.setLv(lv);
            ranking.setHero(hero);
            ranking.setShip(ship);
            ranking.setEnd(end);
            rankingRepository.save(ranking);

            return true;
        }

        rankingRepository.save(new Ranking().builder().nickName(nickName).saveDate(saveDate).name(name).hero(hero).exp(Integer.parseInt(exp.trim())).bounty(Integer.parseInt(bounty.trim())).sailors(sailors).colleagues(colleagues).lv(lv).ship(ship).end(end).build());

        return true;
    }

    @Override
    public boolean createNickname(String nickName, List<Character> list) {
        String decodedNickname = "";
        try{
            decodedNickname = URLDecoder.decode(nickName, "UTF-8");

        }catch (Exception e) {
            System.out.println("디코딩 실패");
        }
        Optional<Nickname> optionalNickname = nr.findById(decodedNickname);
        boolean isValidUser = false;
        Character character = Character.getMainCharacter(list);
        if(character.getIntBounty() >= 200000) {
            isValidUser = true;
        }
        Nickname nickname = Nickname.builder().nickName(decodedNickname).isValidUser(isValidUser).build();

        nr.save(nickname);

        return true;
    }

    @Override
    public boolean register(String itemCode, String itemName) {
        if(itemCode == null || itemName == null) {
            return false;
        }
        Report report = Report.builder().itemCode(itemCode).itemName(itemName).regDt(LocalDateTime.now()).build();
        rr.save(report);

        return true;
    }

    @Override
    @Transactional
    public boolean setImageName() {
        List<Sailor> sailors = sr.findAll();
        for(Sailor s : sailors) {
            if(s.getImageName() == null) {
                s.updateImageName(s);
            }
        }
        return false;
    }


}
