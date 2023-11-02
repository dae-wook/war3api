package com.daesoo.war3api.mrpg.model.dto;

import com.daesoo.war3api.mrpg.model.entity.ItemSetting;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemSettingResponseDto {
    private Long id;
    private String siteNick;
    private String regDt;
    private String title;
    private String items;
    private String checks;
    private String character;

    public ItemSettingResponseDto(ItemSetting itemSetting) {
        this.siteNick = itemSetting.getUser().getSiteNick();
        this.regDt = itemSetting.getRegDt().toString();
        this.title = itemSetting.getTitle();
        this.items = itemSetting.getItems();
        this.checks = itemSetting.getChecks();
        this.character = itemSetting.getCharacterName();
        this.id = itemSetting.getId();
    }

    public static ItemSettingResponseDto of(ItemSetting itemSetting) {
        return ItemSettingResponseDto.builder()
                .id(itemSetting.getId())
                .character(itemSetting.getCharacterName())
                .checks(itemSetting.getChecks())
                .items(itemSetting.getItems())
                .build();
    }

}
