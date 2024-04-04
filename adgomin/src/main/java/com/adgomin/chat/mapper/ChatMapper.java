package com.adgomin.chat.mapper;

import com.adgomin.chat.entity.ChatMessageEntity;
import com.adgomin.chat.entity.ChatRoomEntity;
import com.adgomin.join.entity.JoinEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChatMapper {
    void insertChatMessage(ChatMessageEntity message);

    int appConversationChatRoom(ChatRoomEntity chatRoomEntity);

    int appConversationChatMessage(ChatMessageEntity chatMessageEntity);
}
