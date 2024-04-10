package com.adgomin.chat.mapper;

import com.adgomin.chat.entity.ChatMessageEntity;
import com.adgomin.chat.entity.ChatRoomEntity;
import com.adgomin.chat.vo.ChatRoomVO;
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

    ChatRoomVO getChatRoomCount(@Param(value = "userOrder") int userOrder, @Param(value = "chatRoomOrder") int chatRoomOrder);

    ChatMessageEntity[] getChatMessage(@Param(value = "chatRoomOrder") int chatRoomOrder);

    ChatRoomEntity getChatRoomPartner(@Param(value = "chatRoomOrder") int chatRoomOrder);

    ChatMessageEntity getLastMessage(@Param(value = "chatRoomOrder") int chatRoomOrder);

    ChatMessageEntity getIsRead(@Param(value = "userOrder") int userOrder, @Param(value = "chatRoomOrder") int chatRoomOrder);

    int isRead(@Param(value = "userOrder") int userOrder, @Param(value = "chatRoomOrder") int chatRoomOrder, @Param(value = "isRead") int isRead);
}
