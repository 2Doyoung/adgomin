package com.adgomin.chat.mapper;

import com.adgomin.chat.entity.ChatMessageEntity;
import com.adgomin.chat.entity.ChatRoomEntity;
import com.adgomin.join.entity.JoinEntity;
import com.adgomin.media.vo.MediaRegisterVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ChatMapper {
    void insertChatMessage(ChatMessageEntity message);

    int appConversationChatRoom(ChatRoomEntity chatRoomEntity);

    int appConversationChatMessage(ChatMessageEntity chatMessageEntity);

    ChatRoomEntity[] getChatRoom(@Param(value = "userOrder") int userOrder);
}
