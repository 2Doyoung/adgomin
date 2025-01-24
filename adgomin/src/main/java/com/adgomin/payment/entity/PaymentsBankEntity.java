package com.adgomin.payment.entity;

public class PaymentsBankEntity {
    private int paymentBankOrder;
    private int paymentOrder;
    private String bankCode;
    private String bankName;

    public int getPaymentBankOrder() {
        return paymentBankOrder;
    }

    public void setPaymentBankOrder(int paymentBankOrder) {
        this.paymentBankOrder = paymentBankOrder;
    }

    public int getPaymentOrder() {
        return paymentOrder;
    }

    public void setPaymentOrder(int paymentOrder) {
        this.paymentOrder = paymentOrder;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
}
