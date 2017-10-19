package com.code.yashladha.android_user.Models;

public class Payment {
    private long pay_id;
    private String pay_mode;
    private int amount;
    private String date;
    private long uid;


    public Payment(long pay_id, String pay_mode, String date, int amount, long uid) {
        this.pay_id = pay_id;
        this.pay_mode = pay_mode;
        this.date = date;
        this.amount = amount;
        this.uid = uid;
    }

    public long getPay_id() {
        return pay_id;
    }

    public void setPay_id(long pay_id) {
        this.pay_id = pay_id;
    }

    public String getPay_mode() {
        return pay_mode;
    }

    public void setPay_mode(String pay_mode) {
        this.pay_mode = pay_mode;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }
}