package com.platform.naxterbackend.chat.service;

import com.platform.naxterbackend.chat.model.Chat;
import com.platform.naxterbackend.chat.model.ChatMessage;
import com.platform.naxterbackend.chat.model.Message;
import com.platform.naxterbackend.chat.repository.ChatRepository;
import com.platform.naxterbackend.chat.repository.MessageRepository;
import com.platform.naxterbackend.user.model.User;
import com.platform.naxterbackend.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class ChatServiceImpl implements ChatService {

    @Autowired
    private ChatRepository chatRepository;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserService userService;


    @Override
    public Chat getChat(String idUser1, String idUser2) {
        User user1 = this.userService.getUser(idUser1);
        User user2 = this.userService.getUser(idUser2);

        if(Boolean.TRUE.equals(this.chatRepository.existsByUser1AndUser2(user1, user2))) {
            Chat chat = this.chatRepository.findByUser1AndUser2(user1, user2);
            List<Message> messages = this.messageRepository.findByEmitterAndReceiverOrderByDate(idUser1, idUser2, Sort.by(Sort.Direction.ASC, "date"));
            chat.setMessages(messages);

            return chat;
        } else if(Boolean.TRUE.equals(this.chatRepository.existsByUser1AndUser2(user2, user1))) {
            Chat chat = this.chatRepository.findByUser1AndUser2(user2, user1);
            List<Message> messages = this.messageRepository.findByEmitterAndReceiverOrderByDate(idUser1, idUser2, Sort.by(Sort.Direction.ASC, "date"));
            chat.setMessages(messages);

            return chat;
        } else {
            return new Chat(user1, user2, new ArrayList<>());
        }
    }

    @Override
    public Message sendMessage(ChatMessage chatMessage) {
        User emitter = this.userService.getUser(chatMessage.getEmitter());
        User receiver = this.userService.getUser(chatMessage.getReceiver());

        Chat chat = new Chat();
        List<Message> messages = new ArrayList<>();
        if(Boolean.TRUE.equals(chatMessage.getId() != null && !chatMessage.getId().isEmpty())) {
            chat = this.chatRepository.findById(chatMessage.getId()).get();
            messages = chat.getMessages();
        } else {
            chat.setUser1(emitter);
            chat.setUser2(receiver);
            chat.setMessages(messages);
        }

        Message message = new Message();
        message.setEmitter(emitter);
        message.setReceiver(receiver);
        message.setMessage(chatMessage.getMessage());
        message.setDate(new Date());

        Message messageSaved = this.messageRepository.save(message);

        messages.add(messageSaved);

        this.chatRepository.save(chat);

        return messageSaved;
    }
}
