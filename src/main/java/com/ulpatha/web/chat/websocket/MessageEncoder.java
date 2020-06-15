package com.ulpatha.web.chat.websocket;

import com.google.gson.Gson;
import com.ulpatha.web.chat.dto.ChatMessageDto;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class MessageEncoder implements Encoder.Text<ChatMessageDto> {
    @Override
    public String encode(ChatMessageDto message) throws EncodeException {
        Gson gson = new Gson();
        String json;
        try {
            json = gson.toJson(message);
        } finally {

        }
        return json;
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
