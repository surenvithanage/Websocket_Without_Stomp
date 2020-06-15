package com.ulpatha.web.chat.mappers;


import com.ulpatha.web.chat.dto.ChatMessageDto;
import com.ulpatha.web.chat.model.ChatMessage;
import com.ulpatha.web.chat.model.User;

import java.util.ArrayList;
import java.util.List;

public class ChatMessageMapper {
    public static List<ChatMessageDto> mapMessagesToChatDTOs(List<ChatMessage> chatMessages) {
        List<ChatMessageDto> dtos = new ArrayList<ChatMessageDto>();

        for (ChatMessage chatMessage : chatMessages) {
            dtos.add(
                    new ChatMessageDto(
                            chatMessage.getContents(),
                            chatMessage.getAuthorUser().getUserId(),
                            chatMessage.getRecipientUser().getUserId(),
                            chatMessage.getPostId(),
                            chatMessage.getChatuuid(),
                            chatMessage.getTimeSent().toString()
                    )
            );
        }

        return dtos;
    }

    public static ChatMessage mapChatDTOtoMessage(ChatMessageDto dto) {
        return new ChatMessage(

                // only need the id for mapping
                new User(dto.getFromUserId()),
                new User(dto.getToUserId()),
                dto.getContents(),
                dto.getPostId(),
                dto.getChatuuid()
        );
    }
}
