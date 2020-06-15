package com.ulpatha.web.chat.websocket;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.ulpatha.web.chat.dto.ChatMessageDto;
import com.ulpatha.web.chat.exceptions.UserNotFoundException;
import com.ulpatha.web.chat.model.ChatMessage;
import com.ulpatha.web.chat.service.chat.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@ServerEndpoint(value = "/chat/{chatuuid}", decoders = MessageDecoder.class, encoders = MessageEncoder.class, configurator = CustomSpringConfigurator.class)
public class WebSocketConfig {

    @Autowired
    private ChatService chatService;

    public WebSocketConfig() {
    }

    private Session session;
    //private static final TreeMap<String, Session> sessions = new TreeMap<>();
    private static final Multimap<String, Session> sessions = ArrayListMultimap.create();
    private static Set<Session> clients =
            Collections.synchronizedSet(new HashSet<Session>());

    @OnOpen
    public void onOpen(Session session, @PathParam("chatuuid") String chatuuid) throws IOException, EncodeException {

        this.session = session;

        // All established sessions
        clients.add(session);

        // Specific session for each established chat
        sessions.put(chatuuid, session);

        ChatMessageDto message = new ChatMessageDto();
        message.setChatuuid(chatuuid);
        message.setContents("000");
        message.setTimesent(new Date().toString());
        broadcast(message);
    }

    @OnMessage
    public void onMessage(Session session, ChatMessageDto message) throws IOException, EncodeException, UserNotFoundException, ParseException {
        ChatMessage chatMessage = chatService.submitMessage(message);
        ChatMessageDto chatMessageDto = mapperToDto(chatMessage);
        chatMessageDto.setChatuuid(message.getChatuuid());
        broadcast(chatMessageDto);
    }

    @OnClose
    public void onClose(Session session) throws IOException, EncodeException {
        clients.remove(session);
    }

    @OnError
    public void onError(Session session, Throwable throwable) throws IOException, EncodeException {

    }

    private static void broadcast(ChatMessageDto message) throws IOException, EncodeException {
        synchronized (clients) {
            // Iterate over the connected sessions
            // and broadcast the received message
            for (Session client : clients) {
                for (Map.Entry<String, Session> entry : sessions.entries()) {
                    if (entry.getKey().equals(message.getChatuuid())) {
                        if (client.equals(entry.getValue())) {
                            client.getBasicRemote().sendObject(message);
                            break;
                        }
                    }
                }
            }

//            for (Session client : clients) {
//                if (!client.equals(session)) {
//                    client.getBasicRemote().sendObject(message);
//                }
//            }
        }
    }

    private ChatMessageDto mapperToDto(ChatMessage chatMessage) {
        ChatMessageDto chatMessageDto = new ChatMessageDto();
        chatMessageDto.setFromUserId(chatMessage.getAuthorUser().getUserId());
        chatMessageDto.setToUserId(chatMessage.getRecipientUser().getUserId());
        chatMessageDto.setContents(chatMessage.getContents());

        String pattern = "MM/dd/yyyy HH:mm:ss";
        DateFormat df = new SimpleDateFormat(pattern);
        String todayAsString = df.format(chatMessage.getTimeSent());

        chatMessageDto.setTimesent(todayAsString);
        chatMessageDto.setChatuuid(chatMessage.getChatuuid());
        return chatMessageDto;
    }

}


//
//import com.ulpatha.web.chat.service.UserPresenceService;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.messaging.simp.config.ChannelRegistration;
//import org.springframework.messaging.simp.config.MessageBrokerRegistry;
//import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
//import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
//import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

//@Configuration
//@EnableWebSocketMessageBroker
//public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
//    private final int OUTBOUND_CHANNEL_CORE_POOL_SIZE = 8;
//
//    @Override
//    public void configureMessageBroker(MessageBrokerRegistry config) {
//        config.enableSimpleBroker("/topic");
//        config.setApplicationDestinationPrefixes("/app");
//    }
//
//    @Override
//    public void registerStompEndpoints(StompEndpointRegistry registry) {
//        registry.addEndpoint("/ulpatha-websocket").setAllowedOrigins("*");
//        registry.addEndpoint("/ulpatha-websocket").setAllowedOrigins("*").withSockJS();
//    }

//    @Bean
//    public UserPresenceService presenceChannelInterceptor() {
//        return new UserPresenceService();
//    }
//
//    @Override
//    public void configureClientInboundChannel(ChannelRegistration registration) {
//        registration.setInterceptors(presenceChannelInterceptor());
//    }
//
//    @Override
//    public void configureClientOutboundChannel(ChannelRegistration registration) {
//        registration.taskExecutor().corePoolSize(OUTBOUND_CHANNEL_CORE_POOL_SIZE);
//        registration.setInterceptors(presenceChannelInterceptor());
//    }
//}
