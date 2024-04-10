package com.adgomin.chat.vo;

public class ChatMessageVO {
    private int partnerOrder;
    private int userOrder;

    public int getUserOrder() {
        return userOrder;
    }

    public void setUserOrder(int userOrder) {
        this.userOrder = userOrder;
    }

    public int getPartnerOrder() {
        return partnerOrder;
    }

    public void setPartnerOrder(int partnerOrder) {
        this.partnerOrder = partnerOrder;
    }
}
