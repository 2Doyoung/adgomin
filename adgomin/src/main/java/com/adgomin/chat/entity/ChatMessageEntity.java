package com.adgomin.chat.entity;

import com.adgomin.chat.vo.ChatMessageVO;

import java.util.Date;

public class ChatMessageEntity extends ChatMessageVO {
    private int chatOrder;
    private int chatRoomOrder;
    private int senderOrder;
    private int receiverOrder;
    private String message;
    private Date timestamp;
    private int isRead;
    private int mediaOrder;
    private String mediaTitle;
    private String mediaPrice;
    private String thumbnailImgNm;
    private String thumbnailImgFilePath;

    public int getMediaOrder() {
        return mediaOrder;
    }

    public void setMediaOrder(int mediaOrder) {
        this.mediaOrder = mediaOrder;
    }

    public String getMediaTitle() {
        return mediaTitle;
    }

    public void setMediaTitle(String mediaTitle) {
        this.mediaTitle = mediaTitle;
    }

    public String getMediaPrice() {
        return mediaPrice;
    }

    public void setMediaPrice(String mediaPrice) {
        this.mediaPrice = mediaPrice;
    }

    public String getThumbnailImgNm() {
        return thumbnailImgNm;
    }

    public void setThumbnailImgNm(String thumbnailImgNm) {
        this.thumbnailImgNm = thumbnailImgNm;
    }

    public String getThumbnailImgFilePath() {
        return thumbnailImgFilePath;
    }

    public void setThumbnailImgFilePath(String thumbnailImgFilePath) {
        this.thumbnailImgFilePath = thumbnailImgFilePath;
    }

    public int getIsRead() {
        return isRead;
    }

    public void setIsRead(int isRead) {
        this.isRead = isRead;
    }

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
