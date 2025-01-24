package com.adgomin.payment.entity;

import java.util.Date;

public class PaymentFailedEntity {
    private int paymentFailedOrder;
    private int buyerOrder;
    private int sellerOrder;
    private int mediaOrder;
    private int totalAmount;
    private int paymentAmount;
    private String paymentFailedReason;
    private Date createDt;
    private Date modifyDt;

    public int getPaymentFailedOrder() {
        return paymentFailedOrder;
    }

    public void setPaymentFailedOrder(int paymentFailedOrder) {
        this.paymentFailedOrder = paymentFailedOrder;
    }

    public int getBuyerOrder() {
        return buyerOrder;
    }

    public void setBuyerOrder(int buyerOrder) {
        this.buyerOrder = buyerOrder;
    }

    public int getSellerOrder() {
        return sellerOrder;
    }

    public void setSellerOrder(int sellerOrder) {
        this.sellerOrder = sellerOrder;
    }

    public int getMediaOrder() {
        return mediaOrder;
    }

    public void setMediaOrder(int mediaOrder) {
        this.mediaOrder = mediaOrder;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(int paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getPaymentFailedReason() {
        return paymentFailedReason;
    }

    public void setPaymentFailedReason(String paymentFailedReason) {
        this.paymentFailedReason = paymentFailedReason;
    }

    public Date getCreateDt() {
        return createDt;
    }

    public void setCreateDt(Date createDt) {
        this.createDt = createDt;
    }

    public Date getModifyDt() {
        return modifyDt;
    }

    public void setModifyDt(Date modifyDt) {
        this.modifyDt = modifyDt;
    }
}
