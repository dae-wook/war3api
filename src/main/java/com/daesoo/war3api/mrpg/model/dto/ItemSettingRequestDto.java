package com.daesoo.war3api.mrpg.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ItemSettingRequestDto {

    private String title;
    private String items;
    private String character;
    private String checks;

}
