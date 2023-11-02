package com.daesoo.war3api.user.service;

import com.daesoo.war3api.user.model.dto.BookmarkResponseDto;
import com.daesoo.war3api.user.model.dto.UserDto;
import com.daesoo.war3api.user.model.entity.Bookmark;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

public interface UserService {

    String login(String idToken) throws GeneralSecurityException, IOException;
    String testLogin() throws GeneralSecurityException, IOException;
    String register(String idToken, String name) throws GeneralSecurityException, IOException;

    String createBookmark(String token, String target, String category);

    boolean isDuplNick(String nick);

    String deleteBookmark(String token, String target, String category);

    Bookmark getBookmark(String token, String target, String category);

    List<BookmarkResponseDto> getBookmarks(String token, String category);

    String deleteUser(String token);

    UserDto registerGameNick(String gameNick, HttpServletRequest request);
}
