package com.ulpatha.web.chat.service.chat.impl;

import com.google.common.collect.Lists;
import com.ulpatha.web.chat.dto.ChatChannelInitializationDto;
import com.ulpatha.web.chat.dto.ChatChannelListDto;
import com.ulpatha.web.chat.dto.ChatMessageDto;
import com.ulpatha.web.chat.exceptions.IsSameUserException;
import com.ulpatha.web.chat.exceptions.UserNotFoundException;
import com.ulpatha.web.chat.mappers.ChatMessageMapper;
import com.ulpatha.web.chat.model.ChatChannel;
import com.ulpatha.web.chat.model.ChatMessage;
import com.ulpatha.web.chat.model.User;
import com.ulpatha.web.chat.repository.ChatChannelRepository;
import com.ulpatha.web.chat.repository.ChatMessageRepository;
import com.ulpatha.web.chat.repository.UserRepository;
import com.ulpatha.web.chat.service.chat.ChatService;
import com.ulpatha.web.chat.service.user.UserService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatServiceImpl implements ChatService {

    private ChatChannelRepository chatChannelRepository;

    private ChatMessageRepository chatMessageRepository;

    private UserService userService;

    private UserRepository userRepository;

    private final int MAX_PAGABLE_CHAT_MESSAGES = 100;

    @Autowired
    public ChatServiceImpl(ChatChannelRepository chatChannelRepository, ChatMessageRepository chatMessageRepository, UserService userService, UserRepository userRepository) {
        this.chatChannelRepository = chatChannelRepository;
        this.chatMessageRepository = chatMessageRepository;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    public ChatServiceImpl() {
    }

    private String getExistingChannel(ChatChannelInitializationDto chatChannelInitializationDTO) {
        List<ChatChannel> channel = chatChannelRepository
                .findExistingChannel(
                        chatChannelInitializationDTO.getUserIdOne(),
                        chatChannelInitializationDTO.getUserIdTwo(),
                        chatChannelInitializationDTO.getPostId()
                );

        return (channel != null && !channel.isEmpty()) ? channel.get(0).getUuid() : null;
    }

    private String newChatSession(ChatChannelInitializationDto chatChannelInitializationDTO)
            throws BeansException {
        ChatChannel channel = new ChatChannel(
                userService.getUser(chatChannelInitializationDTO.getUserIdOne()),
                userService.getUser(chatChannelInitializationDTO.getUserIdTwo()),
                chatChannelInitializationDTO.getPostId(),
                chatChannelInitializationDTO.getPostTitle()
        );

        chatChannelRepository.save(channel);

        return channel.getUuid();
    }

    public String establishChatSession(ChatChannelInitializationDto chatChannelInitializationDTO)
            throws BeansException, IsSameUserException, UserNotFoundException {
        if (chatChannelInitializationDTO.getUserIdOne() == chatChannelInitializationDTO.getUserIdTwo()) {
            throw new IsSameUserException();
        }

        String uuid = getExistingChannel(chatChannelInitializationDTO);

        // If channel doesn't already exist, create a new one
        return (uuid != null) ? uuid : newChatSession(chatChannelInitializationDTO);
    }

    public ChatMessage submitMessage(ChatMessageDto chatMessageDTO)
            throws BeansException {
        ChatMessage chatMessage = ChatMessageMapper.mapChatDTOtoMessage(chatMessageDTO);
        try {
            User fromAuthor = userRepository.findByUserId(chatMessageDTO.getFromUserId());
            User toUser = userRepository.findByUserId(chatMessageDTO.getToUserId());
            chatMessage.setAuthorUser(toUser);
            chatMessage.setRecipientUser(fromAuthor);
            chatMessageRepository.save(chatMessage);

            User fromUser = userService.getUser(chatMessage.getAuthorUser().getUserId());
            User recipientUser = userService.getUser(chatMessage.getRecipientUser().getUserId());

//            userService.notifyUser(recipientUser,
//                    new NotificationDTO(
//                            "ChatMessageNotification",
//                            fromUser.getName() + " has sent you a message",
//                            chatMessage.getAuthorUser().getUserId()
//                    )
//            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return chatMessage;
    }

    public List<ChatMessageDto> getExistingChatMessages(String channelUuid) {
        ChatChannel channel = chatChannelRepository.getChannelDetails(channelUuid);

        List<ChatMessage> messagesByLatest = null;
        try {
            List<ChatMessage> chatMessages =
                    chatMessageRepository.getExistingChatMessages(
                            channel.getUserOne().getUserId(),
                            channel.getUserTwo().getUserId(),
                            PageRequest.of(0, MAX_PAGABLE_CHAT_MESSAGES),
                            channel.getPostId()
                    );

            messagesByLatest = Lists.reverse(chatMessages);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ChatMessageMapper.mapMessagesToChatDTOs(messagesByLatest);
    }

    @Override
    public boolean deleteChat(String channelUuid) {
        ChatChannel channel = chatChannelRepository.getChannelDetails(channelUuid);
        try {
            chatChannelRepository.delete(channel);
            List<ChatMessage> chatMessages =
                    chatMessageRepository.getExistingChatMessages(
                            channel.getUserOne().getUserId(),
                            channel.getUserTwo().getUserId(),
                            PageRequest.of(0, MAX_PAGABLE_CHAT_MESSAGES),
                            channel.getPostId()
                    );
            for (ChatMessage chatMessage : chatMessages) {
                chatMessageRepository.delete(chatMessage);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<ChatChannelListDto> getExistingChatChannels(long userId) {
        List<ChatChannelListDto> channelListDtoList = null;
        try {
            List<ChatChannel> chatChannelList = chatChannelRepository.findUserChatHistory(userId);
            channelListDtoList = chatChannelList.stream().map(chat -> chatChannelMapper(chat)).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return channelListDtoList;
    }

    ChatChannelListDto chatChannelMapper(ChatChannel chatChannel) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        String formattedDateTime = chatChannel.getCreatedAt().format(formatter);
        ChatChannelListDto chatChannelListDto = new ChatChannelListDto();
        chatChannelListDto.setFromUserId(chatChannel.getUserOne().getUserId());
        chatChannelListDto.setToUserId(chatChannel.getUserTwo().getUserId());
        chatChannelListDto.setPostId(chatChannel.getPostId());
        chatChannelListDto.setUuid(chatChannel.getUuid());
        chatChannelListDto.setCreatedAt(formattedDateTime);
        chatChannelListDto.setFromUserName("Suren (pending dev)");
        chatChannelListDto.setToUserName("Madusha (pending dev)");
        chatChannelListDto.setPostTitle(chatChannel.getPostTitle());
        chatChannelListDto.setImageUrl("https://cdn4.iconfinder.com/data/icons/small-n-flat/24/user-alt-512.png");
        return chatChannelListDto;
    }

}
