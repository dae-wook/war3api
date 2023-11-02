package com.daesoo.war3api.oner.model.dto;

import com.daesoo.war3api.oner.model.entity.Build;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class BuildResponseDto {

    private Long id;
    private String title;
    private String equipment;
    private String sailor;
    private String ship;
    private String colleague;
    private String characterName;
    private String regDt;

    public static BuildResponseDto of(Build build) {
        return BuildResponseDto.builder()
                .id(build.getId())
                .title(build.getTitle())
                .equipment(build.getEquipments())
                .colleague(build.getColleagues())
                .sailor(build.getSailors())
                .ship(build.getShip())
                .characterName(build.getCharacterName())
                .regDt(build.getRegDt().toString())
                .build();

    }

}
