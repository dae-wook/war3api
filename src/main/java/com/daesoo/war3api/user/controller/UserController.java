package com.daesoo.war3api.user.controller;

import com.daesoo.war3api.component.JwtProvider;
import com.daesoo.war3api.dto.ResponseDto;
import com.daesoo.war3api.user.googleAuth.GoogleAuth;
import com.daesoo.war3api.user.model.dto.BookmarkResponseDto;
import com.daesoo.war3api.user.model.dto.UserDto;
import com.daesoo.war3api.user.model.entity.Bookmark;
import com.daesoo.war3api.user.model.entity.User;
import com.daesoo.war3api.user.service.UserService;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

//@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final Gson gson;
    private final GoogleAuth googleAuth;

    private final JwtProvider jwt;

    @PostMapping("/nickname")
    public ResponseDto<UserDto> registerGameNick(@RequestBody String gameNick, HttpServletRequest request) {
        return ResponseDto.success(userService.registerGameNick(gameNick, request));
    }

    @PostMapping("/login")
    public String login(String idToken) throws Exception {
        return userService.login(idToken);
    }
    @PostMapping("/login-test")
    public String login() throws Exception {
        return userService.testLogin();
    }

    @PostMapping("/register")
    public String register(String idToken, String nick) throws Exception {

        return userService.register(idToken, nick);
    }

    @DeleteMapping("")
    public String deleteUser(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        return userService.deleteUser(token);
    }

    @GetMapping("/info")
    public String checkLogin(HttpServletRequest request) {

        String token = request.getHeader("Authorization");
        return jwt.getUserInfo(token);
    }

    @GetMapping("/isDuplNick")
    public boolean test(String nick) throws GeneralSecurityException, IOException {

        return userService.isDuplNick(nick);
    }

    @PostMapping("/bookmark")
    public String createBookmark(HttpServletRequest request,String target, String category) {
        String token = request.getHeader("Authorization");
        return userService.createBookmark(token, target, category);
    }

    @DeleteMapping("/bookmark")
    public String deleteBookmark(HttpServletRequest request, String target,String category) {
        String token = request.getHeader("Authorization");


        return userService.deleteBookmark(token, target, category);
    }

    @GetMapping("/bookmark")
    public Bookmark getBookmark(HttpServletRequest request, String target, String category) {
        String token = request.getHeader("Authorization");
        return userService.getBookmark(token, target, category);

    }

    @GetMapping("/bookmarks")
    public List<BookmarkResponseDto> getBookmarks(HttpServletRequest request, String category) {
        String token = request.getHeader("Authorization");
        return userService.getBookmarks(token, category);
    }


}
