package com.daesoo.war3api.oner.character.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ObjectData {
    private String objectCode;
    private String updateDate;

    public String getObjectCode() {
        return objectCode;
    }

    public String getUpdateDate() {
        return updateDate;
    }
}
