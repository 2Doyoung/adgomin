package com.adgomin.chat.vo;

public class ChatRoomVO {
    private String partnerNickname;
    private String senderNickname;
    private String receiverNickname;
    private int partnerOrder;
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getPartnerOrder() {
        return partnerOrder;
    }

    public void setPartnerOrder(int partnerOrder) {
        this.partnerOrder = partnerOrder;
    }

    public String getPartnerNickname() {
        return partnerNickname;
    }

    public void setPartnerNickname(String partnerNickname) {
        this.partnerNickname = partnerNickname;
    }

    public String getSenderNickname() {
        return senderNickname;
    }

    public void setSenderNickname(String senderNickname) {
        this.senderNickname = senderNickname;
    }

    public String getReceiverNickname() {
        return receiverNickname;
    }

    public void setReceiverNickname(String receiverNickname) {
        this.receiverNickname = receiverNickname;
    }
}
