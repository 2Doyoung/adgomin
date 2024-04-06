package com.adgomin.chat.entity;

import com.adgomin.chat.vo.ChatRoomVO;

import java.util.Date;

public class ChatRoomEntity extends ChatRoomVO {
    private int chatRoomOrder;
    private int senderOrder;
    private int receiverOrder;
    private Date createDt;
    private Date modifyDt;

    public Date getModifyDt() {
        return modifyDt;
    }

    public void setModifyDt(Date modifyDt) {
        this.modifyDt = modifyDt;
    }

    public int getSenderOrder() {
        return senderOrder;
    }

    public void setSenderOrder(int senderOrder) {
        this.senderOrder = senderOrder;
    }

    public int getReceiverOrder() {
        return receiverOrder;
    }

    public void setReceiverOrder(int receiverOrder) {
        this.receiverOrder = receiverOrder;
    }

    public Date getCreateDt() {
        return createDt;
    }

    public void setCreateDt(Date createDt) {
        this.createDt = createDt;
    }

    public int getChatRoomOrder() {
        return chatRoomOrder;
    }

    public void setChatRoomOrder(int chatRoomOrder) {
        this.chatRoomOrder = chatRoomOrder;
    }
}
