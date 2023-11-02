//package com.daesoo.war3api.notification;
//
//import com.daesoo.war3api.component.JwtProvider;
//import com.daesoo.war3api.oner.character.model.Character;
//import com.daesoo.war3api.oner.model.entity.Hero;
//import com.daesoo.war3api.oner.model.entity.Nickname;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.MediaType;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
//
//import javax.servlet.http.HttpServletRequest;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
//@RequiredArgsConstructor
//@Slf4j
//@Controller
//public class NotificationController {
//
//    public static Map<String, SseEmitter> sseEmitters = new ConcurrentHashMap<>();
//    private final JwtProvider jwtProvider;
//
//    @GetMapping(value = "/sub", produces = "text/event-stream")
//    @ResponseBody
//    public SseEmitter subscribe(HttpServletRequest request) {
//        // 토큰에서 user의 pk값 파싱
//        String siteNick = jwtProvider.siteNick(request.getHeader("Authorization"));
//
//        // 현재 클라이언트를 위한 SseEmitter 생성
//        SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
//        try {
//            // 연결!!
//            sseEmitter.send(SseEmitter.event().name("connect"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        // user의 pk값을 key값으로 해서 SseEmitter를 저장
//        sseEmitters.put(siteNick, sseEmitter);
//
//        sseEmitter.onCompletion(() -> sseEmitters.remove(siteNick));
//        sseEmitter.onTimeout(() -> sseEmitters.remove(siteNick));
//        sseEmitter.onError((e) -> sseEmitters.remove(siteNick));
//
//        return sseEmitter;
//    }
//
//
//
//    @GetMapping("/notification")
//    public String notification() {
//        return "/noti/index";
//    }
//}