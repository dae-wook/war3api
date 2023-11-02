package com.daesoo.war3api.mrpg.model.dto;

import com.daesoo.war3api.mrpg.model.entity.ItemSetting;
import com.daesoo.war3api.mrpg.model.entity.ItemSettingShare;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class ItemSettingShareItemsResponseDto {
    private String character;
    private String items;

    public ItemSettingShareItemsResponseDto(ItemSettingShare itemSettingShare) {
        this.character = itemSettingShare.getCharacterName();
        this.items = itemSettingShare.getItems();
    }
}
