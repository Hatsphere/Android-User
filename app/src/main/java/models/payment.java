package com.example.yashladha.android_user;

public class payment
{
    private long pay_id;
    private String pay_mode;
    private int amount;
    private String date;
    private long uid;


    public payment(long pay_id, String pay_mode, String date, int amount, long uid)
    {
        this.pay_id = pay_id;
        this.pay_mode = pay_mode;
        this.date = date;
        this.amount = amount;
        this.uid = uid;
    }

    public long getPay_id()
    {
        return pay_id;
    }

    public int getPay_mode()
    {
        return pay_mode;
    }

    public int getDate()
    {
        return date;
    }

    public int getAmount()
    {
        return amount;
    }

    public long getUid()
    {
        return uid;
    }

}