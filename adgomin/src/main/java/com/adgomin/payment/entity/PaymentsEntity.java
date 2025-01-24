package com.adgomin.payment.entity;

import java.util.Date;

public class PaymentsEntity {
    private int paymentOrder;
    private String orderId;
    private int buyerOrder;
    private int sellerOrder;
    private int mediaOrder;
    private int totalAmount;
    private int paymentAmount;
    private Date createDt;
    private Date modifyDt;
    private String tid;
    private String signature;
    private String status;
    private String paidAt;
    private String payMethod;
    private String goodsName;
    private String approveNo;
    private String buyerName;
    private String buyerTel;
    private String buyerEmail;
    private String receiptUrl;

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaidAt() {
        return paidAt;
    }

    public void setPaidAt(String paidAt) {
        this.paidAt = paidAt;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getApproveNo() {
        return approveNo;
    }

    public void setApproveNo(String approveNo) {
        this.approveNo = approveNo;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getBuyerTel() {
        return buyerTel;
    }

    public void setBuyerTel(String buyerTel) {
        this.buyerTel = buyerTel;
    }

    public String getBuyerEmail() {
        return buyerEmail;
    }

    public void setBuyerEmail(String buyerEmail) {
        this.buyerEmail = buyerEmail;
    }

    public String getReceiptUrl() {
        return receiptUrl;
    }

    public void setReceiptUrl(String receiptUrl) {
        this.receiptUrl = receiptUrl;
    }

    public int getPaymentOrder() {
        return paymentOrder;
    }

    public void setPaymentOrder(int paymentOrder) {
        this.paymentOrder = paymentOrder;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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
