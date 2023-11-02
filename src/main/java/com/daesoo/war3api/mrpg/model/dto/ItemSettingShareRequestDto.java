package com.daesoo.war3api.mrpg.model.dto;

import com.daesoo.war3api.mrpg.model.entity.ItemSetting;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ItemSettingShareRequestDto {
    private String title;
    private String description;

    private String character;
    private String items;

}
