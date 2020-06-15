package com.ulpatha.web.chat.service.chat;

import com.ulpatha.web.chat.dto.ChatChannelInitializationDto;
import com.ulpatha.web.chat.dto.ChatChannelListDto;
import com.ulpatha.web.chat.dto.ChatMessageDto;
import com.ulpatha.web.chat.exceptions.IsSameUserException;
import com.ulpatha.web.chat.exceptions.UserNotFoundException;
import com.ulpatha.web.chat.model.ChatChannel;
import com.ulpatha.web.chat.model.ChatMessage;
import org.springframework.beans.BeansException;

import java.util.List;

public interface ChatService {
    String establishChatSession(ChatChannelInitializationDto chatChannelInitializationDTO)
            throws BeansException, IsSameUserException, UserNotFoundException;

    ChatMessage submitMessage(ChatMessageDto chatMessageDTO)
            throws BeansException, UserNotFoundException;

    List<ChatMessageDto> getExistingChatMessages(String channelUuid);

    boolean deleteChat(String channelUuid);

    List<ChatChannelListDto> getExistingChatChannels(long userId);
}
