package com.daesoo.war3api.oner.model.dto;

import com.daesoo.war3api.oner.model.entity.Hero;
import com.daesoo.war3api.oner.model.entity.Ranking;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RankingResponseDto {

    private String nickName;
    private String saveDate;

    private Hero hero;
    private int exp;
    private int lv;
    private int bounty;
    private String sailors;
    private String colleagues;
    private String ship;

    public static RankingResponseDto of(Ranking ranking) {
        return RankingResponseDto.builder()
                .nickName(ranking.getNickName())
                .saveDate(ranking.getSaveDate())
                .hero(ranking.getHero())
                .exp(ranking.getExp())
                .lv(ranking.getLv())
                .bounty(ranking.getBounty())
                .sailors(ranking.getSailors())
                .colleagues(ranking.getColleagues())
                .ship(ranking.getShip())
                .build();
    }



}
