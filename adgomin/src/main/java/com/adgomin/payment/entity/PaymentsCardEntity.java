package com.adgomin.payment.entity;

public class PaymentsCardEntity {
    private int paymentCardOrder;
    private int paymentOrder;
    private String cardCode;
    private String cardName;
    private String cardNum;
    private String cardQuota;
    private String cardType;
    private String canPartCancel;
    private String acquCardCode;
    private String acquCardName;

    public int getPaymentCardOrder() {
        return paymentCardOrder;
    }

    public void setPaymentCardOrder(int paymentCardOrder) {
        this.paymentCardOrder = paymentCardOrder;
    }

    public int getPaymentOrder() {
        return paymentOrder;
    }

    public void setPaymentOrder(int paymentOrder) {
        this.paymentOrder = paymentOrder;
    }

    public String getCardCode() {
        return cardCode;
    }

    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public String getCardQuota() {
        return cardQuota;
    }

    public void setCardQuota(String cardQuota) {
        this.cardQuota = cardQuota;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCanPartCancel() {
        return canPartCancel;
    }

    public void setCanPartCancel(String canPartCancel) {
        this.canPartCancel = canPartCancel;
    }

    public String getAcquCardCode() {
        return acquCardCode;
    }

    public void setAcquCardCode(String acquCardCode) {
        this.acquCardCode = acquCardCode;
    }

    public String getAcquCardName() {
        return acquCardName;
    }

    public void setAcquCardName(String acquCardName) {
        this.acquCardName = acquCardName;
    }
}
