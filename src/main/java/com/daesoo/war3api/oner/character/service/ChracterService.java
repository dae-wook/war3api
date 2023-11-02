package com.daesoo.war3api.oner.character.service;

import com.daesoo.war3api.oner.character.model.Character;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public interface ChracterService {

//    ArrayList<Character> getCharacterList(String nickName);
    ArrayList<Character> getLimitedCoin(String nickName, HttpServletRequest request);

    boolean isValid(String gameNick);
}
