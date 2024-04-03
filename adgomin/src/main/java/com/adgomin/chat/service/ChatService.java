package com.adgomin.chat.service;

import com.adgomin.chat.entity.ChatEntity;
import com.adgomin.chat.mapper.ChatMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(value = "com.adgomin.chat.service.ChatService")
@Transactional
public class ChatService {

    private final ChatMapper chatMapper;

    @Autowired
    public ChatService(ChatMapper chatMapper) {
        this.chatMapper = chatMapper;
    }

    public void sendMessage(ChatEntity message) {
        chatMapper.insertChatMessage(message);
    }

    public List<ChatEntity> getMessagesByReceiverOrder(int receiverOrder) {
        return chatMapper.getMessagesByReceiverOrder(receiverOrder);
    }
}
