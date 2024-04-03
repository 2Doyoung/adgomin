package com.adgomin.chat.mapper;

import com.adgomin.chat.entity.ChatEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChatMapper {
    void insertChatMessage(ChatEntity message);

    List<ChatEntity> getMessagesByReceiverOrder(int receiverOrder);
}
