package com.code.yashladha.android_user.Models;

public class Payment {
    private long pay_id;
    private String pay_mode;
    private int amount;
    private String date;
    private String uid;

    public Payment(long pay_id, String pay_mode, int amount, String date, String uid) {
        this.pay_id = pay_id;
        this.pay_mode = pay_mode;
        this.amount = amount;
        this.date = date;
        this.uid = uid;
    }

    public long getPay_id() {
        return pay_id;
    }

    public String getPay_mode() {
        return pay_mode;
    }

    public int getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }

    public String getUid() {
        return uid;
    }

   }