package com.adgomin.payment.entity;

public class PaymentsVbankEntity {
    private int paymentVbankOrder;
    private int paymentOrder;
    private String vbankCode;
    private String vbankName;
    private String vbankNumber;
    private String vbankExpDate;
    private String vbankHolder;

    public int getPaymentVbankOrder() {
        return paymentVbankOrder;
    }

    public void setPaymentVbankOrder(int paymentVbankOrder) {
        this.paymentVbankOrder = paymentVbankOrder;
    }

    public int getPaymentOrder() {
        return paymentOrder;
    }

    public void setPaymentOrder(int paymentOrder) {
        this.paymentOrder = paymentOrder;
    }

    public String getVbankCode() {
        return vbankCode;
    }

    public void setVbankCode(String vbankCode) {
        this.vbankCode = vbankCode;
    }

    public String getVbankName() {
        return vbankName;
    }

    public void setVbankName(String vbankName) {
        this.vbankName = vbankName;
    }

    public String getVbankNumber() {
        return vbankNumber;
    }

    public void setVbankNumber(String vbankNumber) {
        this.vbankNumber = vbankNumber;
    }

    public String getVbankExpDate() {
        return vbankExpDate;
    }

    public void setVbankExpDate(String vbankExpDate) {
        this.vbankExpDate = vbankExpDate;
    }

    public String getVbankHolder() {
        return vbankHolder;
    }

    public void setVbankHolder(String vbankHolder) {
        this.vbankHolder = vbankHolder;
    }
}
