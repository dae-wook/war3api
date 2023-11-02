package com.daesoo.war3api.mrpg.model.dto;

import com.daesoo.war3api.mrpg.model.entity.ItemSetting;
import com.daesoo.war3api.mrpg.model.entity.ItemSettingShare;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ItemSettingShareResponseDto {
    private Long id;
    private String title;
    private String description;
    private String items;
    private String character;
    private String siteNick;
    private int copyCount;
    private int commentCount;
    private String regDt;
    private String modDt;



    public static ItemSettingShareResponseDto of(ItemSettingShare share) {
        return ItemSettingShareResponseDto.builder()
                .id(share.getId())
                .title(share.getTitle())
                .description(share.getDescription())
                .regDt(share.getRegDt().toString())
                .modDt(share.getModDt().toString())
                .copyCount(share.getCopyCount())
                .items(share.getItems())
                .character(share.getCharacterName())
                .siteNick(share.getUser().getSiteNick())
                .commentCount(share.getCommentCount()< 0 ? 0 : share.getCommentCount())
                .build();
    }

}
