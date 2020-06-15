package com.ulpatha.web.chat.controller;

import com.ulpatha.web.chat.dto.*;
import com.ulpatha.web.chat.exceptions.IsSameUserException;
import com.ulpatha.web.chat.exceptions.UserNotFoundException;
import com.ulpatha.web.chat.http.JSONResponseHelper;
//import com.ulpatha.web.chat.model.ChatMessage;
import com.ulpatha.web.chat.model.User;
import com.ulpatha.web.chat.service.chat.ChatService;
import com.ulpatha.web.chat.service.user.UserService;
//import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.messaging.handler.annotation.DestinationVariable;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@CrossOrigin
public class ChatChannelController {
    @Autowired
    private ChatService chatService;

    @Autowired
    private UserService userService;

//    @MessageMapping("/private.chat.{channelId}")
//    @SendTo("/topic/private.chat.{channelId}")
//    public ChatResponseDto chatMessage(@DestinationVariable String channelId, ChatMessageDto message)
//            throws BeansException, UserNotFoundException {
//        ChatMessage chatMessage = chatService.submitMessage(message);
//        ChatResponseDto chatResponseDto = mapperToDto(chatMessage);
//        return chatResponseDto;
//    }

//    public ChatResponseDto mapperToDto(ChatMessage chatMessage) {
//        ChatResponseDto chatResponseDto = new ChatResponseDto();
//        chatResponseDto.setFromUserId(chatMessage.getAuthorUser().getUserId());
//        chatResponseDto.setToUserId(chatMessage.getRecipientUser().getUserId());
//        chatResponseDto.setContents(chatMessage.getContents());
//        chatResponseDto.setReceivedDate(chatMessage.getTimeSent().toString());
//        return chatResponseDto;
//    }

    @RequestMapping(value = "/api/private-chat/channel", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
    public ResponseEntity<String> establishChatChannel(@RequestBody ChatChannelInitializationDto chatChannelInitialization)
            throws IsSameUserException, UserNotFoundException {
        String channelUuid = chatService.establishChatSession(chatChannelInitialization);
        User userOne = userService.getUser(chatChannelInitialization.getUserIdOne());
        User userTwo = userService.getUser(chatChannelInitialization.getUserIdTwo());

        EstablishedChatChannelDto establishedChatChannel = new EstablishedChatChannelDto(
                channelUuid,
                userOne.getName(),
                userTwo.getName(),
                chatChannelInitialization.getPostId()
        );

        return JSONResponseHelper.createResponse(establishedChatChannel, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/private-chat/channel/{channelUuid}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<String> getExistingChatMessages(@PathVariable("channelUuid") String channelUuid) {
        List<ChatMessageDto> messages = chatService.getExistingChatMessages(channelUuid);

        return JSONResponseHelper.createResponse(messages, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/private-chat/channel/delete/{channelUuid}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> deleteExisitingChat(@PathVariable("channelUuid") String channelUuid) {
        boolean result = chatService.deleteChat(channelUuid);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/private-chat/channel/list/{userId}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> findExisitingChatHistory(@PathVariable("userId") String userId) {
        List<ChatChannelListDto> existingChatChannels = chatService.getExistingChatChannels(Long.parseLong(userId));
        return JSONResponseHelper.createResponse(existingChatChannels, HttpStatus.OK);
    }
}
