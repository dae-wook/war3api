package com.daesoo.war3api.user.model.dto;

import com.daesoo.war3api.user.model.entity.Bookmark;
import lombok.Data;

@Data
public class BookmarkResponseDto {

    private String target;

    public BookmarkResponseDto(Bookmark bookmark) {
        this.target = bookmark.getTarget();
    }

}
