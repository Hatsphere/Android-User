package com.example.yashladha.android_user;

public class order
{
    private long order_id;
    private String status;
    private long pay_id;
    private long uid;
    private String order_date;

    public order(long order_id, String status, long pay_id, long uid, String order_date)
    {
        this.order_id = order_id;
        this.status = status;
        this.pay_id = pay_id;
        this.uid = uid;
        this.order_date = order_date;
    }

    public long getOrder_id()
    {
        return order_id;
    }

    public String getStatus()
    {
        return status;
    }

    public long getPay_id()
    {
        return pay_id;
    }

    public long getUid()
    {
        return uid;
    }
    public String getOrder_date()
    {
        return order_date;
    }
}