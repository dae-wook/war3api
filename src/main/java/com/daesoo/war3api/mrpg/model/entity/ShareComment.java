package com.daesoo.war3api.mrpg.model.entity;

import com.daesoo.war3api.entity.Timestamp;
import com.daesoo.war3api.mrpg.model.dto.CommentRequestDto;
import com.daesoo.war3api.user.model.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ShareComment extends Timestamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @ManyToOne
    private User user;

    @ManyToOne
    private ItemSettingShare itemSettingShare;

    public ShareComment(String content, User user, ItemSettingShare itemSettingShare) {
        this.content = content;
        this.user = user;
        this.itemSettingShare = itemSettingShare;
    }

    public void update(CommentRequestDto requestDto) {
        this.content = requestDto.getContent();
    }
}
