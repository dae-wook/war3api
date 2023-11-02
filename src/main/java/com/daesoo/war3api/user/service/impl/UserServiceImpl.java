package com.daesoo.war3api.user.service.impl;

import com.daesoo.war3api.chat.dto.ChatRoomResponseDto;
import com.daesoo.war3api.chat.entity.ChatRoom;
import com.daesoo.war3api.chat.entity.ChatUser;
import com.daesoo.war3api.chat.entity.RoomType;
import com.daesoo.war3api.component.JwtProvider;
import com.daesoo.war3api.oner.character.service.ChracterService;
import com.daesoo.war3api.user.googleAuth.GoogleAuth;
import com.daesoo.war3api.user.model.dto.BookmarkResponseDto;
import com.daesoo.war3api.user.model.dto.UserDto;
import com.daesoo.war3api.user.model.entity.Bookmark;
import com.daesoo.war3api.user.model.entity.User;
import com.daesoo.war3api.user.repository.BookmarkRepository;
import com.daesoo.war3api.user.repository.UserRepository;
import com.daesoo.war3api.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BookmarkRepository bookmarkRepository;
    private final ChracterService chracterService;
    private final JwtProvider jwtProvider;

    private final GoogleAuth googleAuth;

    @Override
    public String login(String idToken) throws GeneralSecurityException, IOException {
        String email = "";
        if (idToken.contains("@")) {
            email = idToken;
        }
        email = googleAuth.getUserEmail(idToken);
        if (email != null) {
            Optional<User> optionalUser = userRepository.findById(email);

            if (optionalUser.isPresent()) {
                //회원이면 토큰 생성 후 리턴
                User user = optionalUser.get();
                LocalDateTime now = LocalDateTime.now();
                if (user.getJoinLimit().isAfter(now)) {
                    return "{\"type\":\"ban\",\"login_limit\":\"" + user.getJoinLimit() + "\"}";
                }
                String token = jwtProvider.createAccessToken(user);
                return "{\"type\":\"login\",\"token\":\"" + token + "\"}";

            } else {
                return "{\"type\":\"join\"}";
            }
        }
        //회원이 아니면 not user 리턴

        return "{\"msg\":\"bad request\"}";
    }

    @Override
    public String register(String idToken, String name) throws GeneralSecurityException, IOException {

        String email = googleAuth.getUserEmail(idToken);
        if (isDuplNick(name)) {
            return "\"type\": \"duplicate nickname\"";
        }
        User user = User.builder()
                .email(email)
                .siteNick(name)
                .regDt(LocalDateTime.now())
                .joinLimit(LocalDateTime.now())
                .role("USER")
                .build();

        userRepository.save(user);

        return login(idToken);
    }

    @Override
    public boolean isDuplNick(String nick) {

        Optional<User> optionalUser = userRepository.findBySiteNick(nick);

        if (optionalUser.isPresent()) {
            return true;
        }

        return false;
    }

    @Override
    public String createBookmark(String token, String target, String category) {
        if (jwtProvider.checkJwt(token)) {
            String siteNick = jwtProvider.siteNick(token);
            Optional<Bookmark> optionalBookmark = bookmarkRepository.findBySiteNickAndTargetAndCategory(siteNick, target, category);
            if (optionalBookmark.isPresent()) {
                return "{\"msg\":\"already exist\"}";
            } else {
                Bookmark bookmark = Bookmark.builder()
                        .siteNick(siteNick)
                        .target(target)
                        .category(category)
                        .build();
                bookmarkRepository.save(bookmark);
                return "{\"msg\":\"success\"}";
            }
        } else {
            return "{\"msg\":\"false\"}";
        }
    }

    @Override
    public String deleteBookmark(String token, String target, String category) {
        if (jwtProvider.checkJwt(token)) {

            String siteNick = jwtProvider.siteNick(token);

            Optional<Bookmark> optionalBookmark = bookmarkRepository.findBySiteNickAndTargetAndCategory(siteNick, target, category);
            if (optionalBookmark.isPresent()) {
                Bookmark bookmark = optionalBookmark.get();
                bookmarkRepository.deleteById(bookmark.getId());
                return "{\"msg\":\"success\"}";
            }
        }

        return "{\"msg\":\"false\"}";
    }

    @Override
    public Bookmark getBookmark(String token, String target, String category) {
        if (jwtProvider.checkJwt(token)) {
            String siteNick = jwtProvider.siteNick(token);
            Optional<Bookmark> optionalBookmark = bookmarkRepository.findBySiteNickAndTargetAndCategory(siteNick, target, category);
            return optionalBookmark.orElse(null);
        }

        return null;
    }

    @Override
    public List<BookmarkResponseDto> getBookmarks(String token, String category) {
        if (jwtProvider.checkJwt(token)) {
            String siteNick = jwtProvider.siteNick(token);
            List<Bookmark> list = bookmarkRepository.findAllBySiteNickAndCategory(siteNick, category);
            List<BookmarkResponseDto> dtoList = new ArrayList<>();
            for (Bookmark m : list) {
                dtoList.add(new BookmarkResponseDto(m));
            }
            return dtoList;
        } else return null;
    }

    @Override
    public String deleteUser(String token) {
        if(jwtProvider.checkJwt(token)) {
            String siteNick = jwtProvider.siteNick(token);
            User user = userRepository.findBySiteNick(siteNick).orElseThrow(() -> new NullPointerException("아이디가 존재하지 않음."));
            userRepository.deleteById(user.getEmail());
            return "success";
        }
        return "fail";
    }

    @Override
    @Transactional
    public UserDto registerGameNick(String gameNick, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (jwtProvider.checkJwt(token)) {
            User user = userRepository.findBySiteNick(jwtProvider.siteNick(token)).orElseThrow(() ->new NullPointerException("유저 정보가 없습니다."));
            if(chracterService.isValid(gameNick)) {
                user.updateGameNick(gameNick);
            }
            return UserDto.of(user);

        } else {
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
        }
    }

    @Override
    public String testLogin() throws GeneralSecurityException, IOException {
            //회원이면 토큰 생성 후 리턴
        User user = userRepository.findBySiteNick("muhantry").get();
        LocalDateTime now = LocalDateTime.now();
        if (user.getJoinLimit().isAfter(now)) {
            return "{\"type\":\"ban\",\"login_limit\":\"" + user.getJoinLimit() + "\"}";
        }
        String token = jwtProvider.createAccessToken(user);
        return "{\"type\":\"login\",\"token\":\"" + token + "\"}";


        //회원이 아니면 not user 리턴

    }
}



