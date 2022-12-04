package com.platform.naxterbackend.chat.service;

import com.platform.naxterbackend.chat.model.Chat;
import com.platform.naxterbackend.chat.model.ChatMessage;
import com.platform.naxterbackend.chat.model.Message;

public interface ChatService {

    Chat getChat(String idUser1, String idUser2);

    Message sendMessage(ChatMessage chatMessage);
}
