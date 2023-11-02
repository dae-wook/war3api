package com.daesoo.war3api.mrpg.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CountResponseDto {
    private String character;
    private Long count;
}
