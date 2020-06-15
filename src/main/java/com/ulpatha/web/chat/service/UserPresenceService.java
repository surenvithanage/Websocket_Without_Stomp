//package com.ulpatha.web.chat.service;
//
//import com.ulpatha.web.chat.model.User;
//import com.ulpatha.web.chat.service.user.UserService;
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.messaging.Message;
//import org.springframework.messaging.MessageChannel;
//import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
//import org.springframework.messaging.support.ChannelInterceptorAdapter;
//import org.springframework.stereotype.Component;
//
//@Component
//public class UserPresenceService extends ChannelInterceptorAdapter {
//
//    @Autowired
//    private UserService userService;
//
//    @Override
//    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
//        StompHeaderAccessor stompDetails = StompHeaderAccessor.wrap(message);
//
//        if (stompDetails.getCommand() == null) {
//            return;
//        }
//
//        System.out.println("------------------------------");
//        System.out.println(message);
//        System.out.println(channel);
//        System.out.println(sent);
//        System.out.println(stompDetails);
//        System.out.println(stompDetails.getUser());
//
//        switch (stompDetails.getCommand()) {
//            case CONNECT:
//            case CONNECTED:
//                toggleUserPresence(stompDetails.getUser().getName(), true);
//                break;
//            case DISCONNECT:
//                toggleUserPresence(stompDetails.getUser().getName(), false);
//                break;
//            default:
//                break;
//        }
//    }
//
//    private void toggleUserPresence(String userEmail, Boolean isPresent) {
//        try {
//            User user = userService.getUser(userEmail);
//            userService.setIsPresent(user, isPresent);
//        } catch (BeansException e) {
//            e.printStackTrace();
//        }
//    }
//}
