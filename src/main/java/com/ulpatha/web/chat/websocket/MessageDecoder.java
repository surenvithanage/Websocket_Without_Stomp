package com.ulpatha.web.chat.websocket;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.ulpatha.web.chat.dto.ChatMessageDto;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

public class MessageDecoder implements Decoder.Text<ChatMessageDto> {
    @Override
    public ChatMessageDto decode(String s) throws DecodeException {
        Gson gson = new Gson();
        ChatMessageDto message = null;
        try {
            message = gson.fromJson(s, ChatMessageDto.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return message;
    }

    @Override
    public boolean willDecode(String s) {
        return (s != null);
    }

    @Override
    public void init(EndpointConfig endpointConfig) {
        // Custom initialization logic
    }

    @Override
    public void destroy() {
        // Close resources
    }
}
