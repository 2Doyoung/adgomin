package com.adgomin.chat.entity;

import java.util.Date;

public class ChatMessageEntity {
    private int chatOrder;
    private int chatRoomOrder;
    private int senderOrder;
    private int receiverOrder;
    private String message;
    private Date timestamp;

    public int getChatRoomOrder() {
        return chatRoomOrder;
    }

    public void setChatRoomOrder(int chatRoomOrder) {
        this.chatRoomOrder = chatRoomOrder;
    }

    public int getChatOrder() {
        return chatOrder;
    }

    public void setChatOrder(int chatOrder) {
        this.chatOrder = chatOrder;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
