package com.daesoo.war3api.mrpg.model.entity;

import com.daesoo.war3api.entity.Timestamp;
import com.daesoo.war3api.mrpg.model.dto.ItemSettingShareRequestDto;
import com.daesoo.war3api.user.model.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class ItemSettingShare extends Timestamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    @Column(length = 5000)
    private String description;
    private int copyCount;

    private int commentCount;

    @OneToMany(mappedBy = "itemSettingShare", cascade = CascadeType.REMOVE)
    private List<ShareComment> comments = new ArrayList<>();

//    @OneToOne
//    private ItemSetting itemSetting;

    private String items;
    private String characterName;

    @ManyToOne
    private User user;

    public ItemSettingShare(ItemSettingShareRequestDto requestDto, User user, ItemSetting itemSetting) {
        this.title = requestDto.getTitle();
        this.description = requestDto.getDescription();
        this.items = itemSetting.getItems();
        this.characterName = itemSetting.getCharacterName();
        this.copyCount = 0;
        this.user = user;
//        this.itemSetting = itemSetting;
    }


    public void update(ItemSettingShareRequestDto requestDto) {

        this.title = requestDto.getTitle();
        this.description = requestDto.getDescription();
        this.characterName = requestDto.getCharacter();
        this.items = requestDto.getItems();
    }

    public void addCommentCount() {
        this.commentCount++;
    }

    public void subtractCommentCount() {
        this.commentCount--;
    }
}
