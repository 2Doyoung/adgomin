package com.adgomin.chat.service;

import com.adgomin.chat.entity.ChatMessageEntity;
import com.adgomin.chat.entity.ChatRoomEntity;
import com.adgomin.chat.mapper.ChatMapper;
import com.adgomin.chat.vo.ChatRoomVO;
import com.adgomin.enums.CommonResult;
import com.adgomin.join.entity.JoinEntity;
import com.adgomin.join.vo.JoinVO;
import com.adgomin.media.vo.MediaRegisterVO;
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

        ChatRoomVO chatRoomVO = this.chatMapper.getExistChatRoomCount(chatRoomEntity);

        int result = 0;
        int result1 = 0;
        int result2 = 0;

        if(chatRoomVO.getCount() > 0) {
            ChatRoomEntity chatRoomEntity1 = this.chatMapper.getExistChatRoom(chatRoomEntity);
            chatMessageEntity.setChatRoomOrder(chatRoomEntity1.getChatRoomOrder());
            result1 = 1;
        } else if(chatRoomVO.getCount() == 0) {
            result1 = this.chatMapper.appConversationChatRoom(chatRoomEntity);
            chatMessageEntity.setChatRoomOrder(chatRoomEntity.getChatRoomOrder());
        }

        if(result1 > 0) {
            result2 = this.chatMapper.appConversationChatMessage(chatMessageEntity);
        }

        if(result1 > 0 && result2 > 0) {
            result = 1;
        }

        return result > 0 ? CommonResult.SUCCESS : CommonResult.FAILURE;
    }

    public ChatRoomEntity[] getChatRoom(int userOrder) {
        return this.chatMapper.getChatRoom(userOrder);
    }
    public ChatRoomVO getChatRoomCount(int userOrder, int chatRoomOrder) {
        return this.chatMapper.getChatRoomCount(userOrder, chatRoomOrder);
    }

    public ChatMessageEntity[] getChatMessage(int userOrder) {
        return this.chatMapper.getChatMessage(userOrder);
    }

    public ChatRoomEntity getChatRoomPartner(int chatRoomOrder) {
        return this.chatMapper.getChatRoomPartner(chatRoomOrder);
    }

    public ChatMessageEntity getLastMessage(int chatRoomOrder) {
        return this.chatMapper.getLastMessage(chatRoomOrder);
    }

    public ChatMessageEntity getIsRead(int userOrder, int chatRoomOrder) {
        return this.chatMapper.getIsRead(userOrder, chatRoomOrder);
    }

    public Enum<?> isRead(JoinVO joinVO, ChatMessageEntity chatMessageEntity) {
        return this.chatMapper.isRead(joinVO.getUserOrder(), chatMessageEntity.getChatRoomOrder(), chatMessageEntity.getIsRead()) > 0 ? CommonResult.SUCCESS : CommonResult.FAILURE;
    }
}
