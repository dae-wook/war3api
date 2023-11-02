package com.daesoo.war3api.mrpg.model.entity;


import com.daesoo.war3api.entity.Timestamp;
import com.daesoo.war3api.mrpg.model.dto.ItemSettingRequestDto;
import com.daesoo.war3api.user.model.entity.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemSetting extends Timestamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    private String title;

    @Column(length = 1000)
    private String items;

    private String characterName;

    @Column(length = 2500)
    private String checks;

//    private LocalDateTime regDt;

    public ItemSetting(ItemSettingRequestDto itemSettingRequestDto, User user){
        this.title = itemSettingRequestDto.getTitle();
        this.items = itemSettingRequestDto.getItems();
        this.checks = itemSettingRequestDto.getChecks();
        this.characterName = itemSettingRequestDto.getCharacter();
        this.user = user;
//        this.regDt = LocalDateTime.now();
    }

    public static ItemSetting copyItemSetting(ItemSettingShare itemSettingShare, User user) {
        return ItemSetting.builder()
                .user(user)
                .title("(복사됨) " + itemSettingShare.getTitle())
                .items(itemSettingShare.getItems())
                .characterName(itemSettingShare.getCharacterName())
                .build();
    }

    public void updateChecks(String checks) {
        this.checks = checks;
    }


    public void update(ItemSettingRequestDto itemSettingRequestDto) {
        this.title = itemSettingRequestDto.getTitle();
        this.items = itemSettingRequestDto.getItems();
        this.checks = itemSettingRequestDto.getChecks();
        this.characterName = itemSettingRequestDto.getCharacter();
    }
}
