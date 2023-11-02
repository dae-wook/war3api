//package com.daesoo.war3api.notification;
//
//import com.daesoo.war3api.chat.entity.ChatRoom;
//import lombok.RequiredArgsConstructor;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
//
//import java.nio.charset.StandardCharsets;
//import java.util.Set;
//
//import static com.daesoo.war3api.notification.NotificationController.sseEmitters;
//
//@RequiredArgsConstructor
//@Service
//public class NotificationService {
//
//
//    public void notifyAddCommentEvent(ChatRoom chatRoom) {
//
//        try {
//            Set<String> keySet = sseEmitters.keySet();
//            for(String key : keySet) {
//                SseEmitter sseEmitter = sseEmitters.get(key);
//                byte[] data = (chatRoom.getRoomType().getName() + "%SPACE%" + chatRoom.getTitle() + "%SPACE%" + chatRoom.getHost()).getBytes(StandardCharsets.UTF_8);
//                sseEmitter.send(SseEmitter.event().name("createRoom").data(data));
//            }
//        } catch (Exception e) {
//        }
//    }
//
////    @Scheduled(fixedDelay = 1000 * 50)
////    public void refresh() {
////        try {
////            Set<String> keySet = sseEmitters.keySet();
////            for(String key : keySet) {
////                SseEmitter sseEmitter = sseEmitters.get(key);
////                sseEmitter.send(SseEmitter.event().name("refresh").data("refresh"));
////            }
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
////    }
//}
