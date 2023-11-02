package com.daesoo.war3api.chat.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ChatRoomRequestDto {

    private String title;
    private String gameType;
    private int capacity;
    private String host;
    private String status;
    private Long roomTypeId;
    private Boolean isNeedHelper;
    private Boolean isAllowBeginner;

}
