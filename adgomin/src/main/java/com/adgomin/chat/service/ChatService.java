package com.adgomin.chat.service;

import com.adgomin.chat.entity.ChatMessageEntity;
import com.adgomin.chat.entity.ChatRoomEntity;
import com.adgomin.chat.mapper.ChatMapper;
import com.adgomin.enums.CommonResult;
import com.adgomin.join.entity.JoinEntity;
import com.adgomin.join.vo.JoinVO;
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

    public void sendMessage(ChatMessageEntity message) {
        chatMapper.insertChatMessage(message);
    }

    public Enum<?> appConversation(JoinVO joinVO, ChatMessageEntity chatMessageEntity) {
        ChatRoomEntity chatRoomEntity = new ChatRoomEntity();

        chatRoomEntity.setReceiverOrder(chatMessageEntity.getReceiverOrder());
        chatRoomEntity.setSenderOrder(joinVO.getUserOrder());
        chatMessageEntity.setSenderOrder(joinVO.getUserOrder());

        int result = 0;

        int result1 = this.chatMapper.appConversationChatRoom(chatRoomEntity);

        int result2 = 0;

        if(result1 > 0) {
            chatMessageEntity.setChatRoomOrder(chatRoomEntity.getChatRoomOrder());
            result2 = this.chatMapper.appConversationChatMessage(chatMessageEntity);
        }

        if(result1 > 0 && result2 > 0) {
            result = 1;
        }

        return result > 0 ? CommonResult.SUCCESS : CommonResult.FAILURE;
    }
}
