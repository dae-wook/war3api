package com.daesoo.war3api.chat.service;

import com.daesoo.war3api.chat.dto.*;
import com.daesoo.war3api.chat.entity.ChatRoom;
import com.daesoo.war3api.chat.entity.ChatUser;
import com.daesoo.war3api.chat.entity.RoomType;
import com.daesoo.war3api.chat.repository.ChatRoomRepository;
import com.daesoo.war3api.chat.repository.ChatUserRepository;
import com.daesoo.war3api.chat.repository.RoomTypeRepository;
import com.daesoo.war3api.component.JwtProvider;
//import com.daesoo.war3api.notification.NotificationService;
import com.daesoo.war3api.oner.model.dto.RankingResponseDto;
import com.daesoo.war3api.oner.model.entity.Ranking;
import com.daesoo.war3api.user.model.entity.User;
import com.daesoo.war3api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatUserRepository chatUserRepository;
    private final UserRepository userRepository;
//    private final NotificationService notificationService;
    private final JwtProvider jwtProvider;
    private final RoomTypeRepository roomTypeRepository;

    public List<ChatRoomResponseDto> getRoomList(int page, int size, Long roomTypeId) {
        Sort sort = Sort.by(Sort.Direction.DESC, "memberCount", "regDt");
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<ChatRoom> chatRooms;
        if (roomTypeId == 999L) {
            chatRooms = chatRoomRepository.findAllByChatUsersIsNotNull(pageable);
        } else {
            chatRooms = chatRoomRepository.findAllByRoomTypeIdAndChatUsersIsNotNull(roomTypeId, pageable);
        }

//        HashSet<ChatRoom> chatRoomSet = new HashSet<>();
        LinkedHashSet<ChatRoom> chatRoomSet = new LinkedHashSet<>();
        for(ChatRoom chatRoom : chatRooms) {
            chatRoomSet.add(chatRoom);
        }
        List<ChatRoomResponseDto> dtoList = new ArrayList();
        for(ChatRoom chatRoom : chatRoomSet) {
            dtoList.add(ChatRoomResponseDto.of(chatRoom));
        }
        return dtoList;
    }

    public ChatRoomResponseDto createRoom(ChatRoomRequestDto chatRoomRequestDto, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (jwtProvider.checkJwt(token)) {
            User user = userRepository.findBySiteNick(jwtProvider.siteNick(token)).orElseThrow(() ->new NullPointerException("유저 정보가 없습니다."));
            RoomType roomType = roomTypeRepository.findById(chatRoomRequestDto.getRoomTypeId()).orElseThrow( () -> new NullPointerException("룸 타입 정보가 없습니다."));
            ChatRoom chatRoom = ChatRoom.create(chatRoomRequestDto, roomType, user);
//            ChatUser chatUser = new ChatUser(user, chatRoom);
            chatRoomRepository.save(chatRoom); // 채팅방 저장
//            notificationService.notifyAddCommentEvent(chatRoom);
//            chatUserRepository.save(chatUser); // 채팅방 만든사람 ChatUser 저장
//            chatRoom.initChatUsers(chatUser);
            return ChatRoomResponseDto.of(chatRoom);
        } else {
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
        }
    }

    @Transactional
    public ChatUserResponseDto createChatUser(Long roomId, ChatUserRequestDto dto, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (jwtProvider.checkJwt(token)) {
            User user = userRepository.findBySiteNick(jwtProvider.siteNick(token)).orElseThrow(() ->new NullPointerException("유저 정보가 없습니다."));
            ChatRoom chatRoom = chatRoomRepository.findById(roomId).orElseThrow(() ->new NullPointerException("채팅방 정보가 없습니다."));
            if(chatRoom.getChatUsers().size() >= chatRoom.getCapacity()) {
                throw new IllegalArgumentException("방이 가득찼습니다.");
            }
            ChatUser chatUser = ChatUser.create(user, dto, chatRoom);
            Optional<ChatUser> differChatUser = chatUserRepository.findByUserEmail(user.getEmail());
            if(differChatUser.isPresent()) {
                ChatUser diffUser= differChatUser.get();
                throw new IllegalArgumentException("이미 다른 채팅방\""+diffUser.getChatRoom().getId()+"\"에 입장한 유저입니다.");
            }
            for(ChatUser inUser : chatRoom.getChatUsers()) {
                if(inUser.getUser().getSiteNick().equals(chatUser.getUser().getSiteNick())) {
                    throw new IllegalArgumentException("이미 입장한 유저입니다.");
                }
            }
            chatRoom.enterUser();
            chatUserRepository.save(chatUser);
            return ChatUserResponseDto.of(chatUser);

        } else {
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
        }
    }

    @Transactional
    public ChatUserResponseDto updatePeerId(String siteNick, ChatUserRequestDto dto) {

        ChatUser chatUser = chatUserRepository.findByUserSiteNick(siteNick).orElseThrow(() -> new NullPointerException("채팅중인 유저가 아닙니다."));
        chatUser.update(dto);

        return ChatUserResponseDto.of(chatUser);

    }

    public ChatRoomResponseDto getRoom(Long roomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId).orElseThrow(() ->new NullPointerException("채팅방 정보가 없습니다."));

        return ChatRoomResponseDto.of(chatRoom);
    }

    public String deleteChatRoom(Long roomId) {
        chatRoomRepository.deleteById(roomId);
        return "삭제 성공";
    }

    @Transactional
    public ChatUserResponseDto deleteChatRoomChatUser(Long roomId, String siteNick) {

        User user = userRepository.findBySiteNick(siteNick).orElseThrow(() ->new NullPointerException("유저 정보가 없습니다."));
        ChatRoom chatRoom = chatRoomRepository.findById(roomId).orElseThrow(() ->new NullPointerException("채팅방 정보가 없습니다."));
        ChatUser chatUser = chatUserRepository.findByChatRoomAndUser(chatRoom, user).orElseThrow(() ->new NullPointerException("채팅중인 유저가 아닙니다."));
        ChatUserResponseDto chatUserResponseDto = ChatUserResponseDto.of(chatUser);
//        if(chatRoom.getChatUsers().size() == 1) {
//            chatRoomRepository.delete(chatRoom);
//            return chatUserResponseDto;
//        }
//        if(chatRoom.getHost().equals(chatUser.getUser().getSiteNick())) { // 나가는 사람이 호스트일때
//            log.info("Host -> {}", chatRoom.getHost());
//            log.info("leave User -> {}", chatUser.getUser().getSiteNick());
//            chatRoom.updateHost(chatRoom.getChatUsers().get(1)); // 참여자중 두번째 유저에게 호스트 양도
//        }
        chatUserRepository.delete(chatUser);
        chatRoom.escapeUser();
        return chatUserResponseDto;
    }

    @Transactional
    public ChatUserResponseDto deleteChatUser(String siteNick) {

        User user = userRepository.findBySiteNick(siteNick).orElseThrow(() ->new NullPointerException("유저 정보가 없습니다."));
        ChatUser chatUser = chatUserRepository.findByUserSiteNick(user.getSiteNick()).orElseThrow(() ->new NullPointerException("채팅중인 유저가 아닙니다."));
        ChatUserResponseDto chatUserResponseDto = ChatUserResponseDto.of(chatUser);
//        if(chatRoom.getChatUsers().size() == 1) {
//            chatRoomRepository.delete(chatRoom);
//            return chatUserResponseDto;
//        }
//        if(chatRoom.getHost().equals(chatUser.getUser().getSiteNick())) { // 나가는 사람이 호스트일때
//            log.info("Host -> {}", chatRoom.getHost());
//            log.info("leave User -> {}", chatUser.getUser().getSiteNick());
//            chatRoom.updateHost(chatRoom.getChatUsers().get(1)); // 참여자중 두번째 유저에게 호스트 양도
//        }
        chatUserRepository.delete(chatUser);
        return chatUserResponseDto;
    }

    @Transactional
    public ChatRoomResponseDto updateChatRoom(Long roomId, ChatRoomRequestDto chatRoomRequestDto, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (jwtProvider.checkJwt(token)) {
            User user = userRepository.findBySiteNick(jwtProvider.siteNick(token)).orElseThrow(() ->new NullPointerException("유저 정보가 없습니다."));
            ChatRoom chatRoom = chatRoomRepository.findById(roomId).orElseThrow(() ->new NullPointerException("채팅방 정보가 없습니다."));
            ChatUser chatUser = chatUserRepository.findByChatRoomAndUser(chatRoom, user).orElseThrow(() ->new NullPointerException("채팅중인 유저가 아닙니다."));
            RoomType roomType = roomTypeRepository.findById(chatRoomRequestDto.getRoomTypeId()).orElseThrow( () -> new NullPointerException("룸 타입 정보가 없습니다."));
//            if(user.getSiteNick().equals(chatRoom.getHost())) {
//                chatRoom.updateChatRoom(chatRoomRequestDto, roomType);
//                return ChatRoomResponseDto.of(chatRoom);
//            } else {
//                throw new IllegalArgumentException("호스트만 수정할 수 있습니다.");
//            }
            chatRoom.updateChatRoom(chatRoomRequestDto, roomType);
            return ChatRoomResponseDto.of(chatRoom);

        } else {
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
        }
    }

    public List<RoomTypeResponseDto> getRoomTypeList() {
        List<RoomType> roomTypes = roomTypeRepository.findAll();
        return roomTypes.stream().map(RoomTypeResponseDto::of).collect(Collectors.toList());
    }

    public Long getUserChatRoom(String siteNick) {
        Long chatRoomId = null;
        Optional<ChatUser> chatUser = chatUserRepository.findByUserSiteNick(siteNick);
        if(chatUser.isPresent()) {
            chatRoomId = chatUser.get().getChatRoom().getId();
        }
        return chatRoomId;
    }

    public List<ChatUserResponseDto> getChatUsers() {
        return chatUserRepository.findAll().stream().map(ChatUserResponseDto::of).collect(Collectors.toList());
    }
}
