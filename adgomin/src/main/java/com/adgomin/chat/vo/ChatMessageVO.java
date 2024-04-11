package com.adgomin.chat.vo;

public class ChatMessageVO {
    private int partnerOrder;
    private int userOrder;
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

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
