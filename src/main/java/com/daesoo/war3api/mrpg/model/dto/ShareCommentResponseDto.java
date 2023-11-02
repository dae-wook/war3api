package com.daesoo.war3api.mrpg.model.dto;

import com.daesoo.war3api.mrpg.model.entity.ShareComment;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
public class ShareCommentResponseDto {

    private Long id;
    private String content;
    private String siteNick;
    private String regDt;
    private String modDt;

    public static ShareCommentResponseDto of(ShareComment shareComment) {
        return ShareCommentResponseDto.builder()
                .id(shareComment.getId())
                .content(shareComment.getContent())
                .siteNick(shareComment.getUser().getSiteNick())
                .regDt(shareComment.getRegDt().toString())
                .modDt(shareComment.getModDt().toString())
                .build();
    }

}
