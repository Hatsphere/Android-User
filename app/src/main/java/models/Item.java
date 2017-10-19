package com.example.yashladha.android_user;

public class item
{
    private long item_id;
    private String order_date;
    private long uid;
    private long pid;
    private long order_id;

    public item(long item_id, String order_date, long uid, long pid, long order_id)
    {
        this.item_id = item_id;
        this.order_date = order_date;
        this.uid = uid;
        this.pid = pid;
        this.order_id = order_id;
    }

    public long getItem_id()
    {
        return item_id;
    }
    public String getOrder_date()
    {
        return order_date;
    }
    public long getUid()
    {
        return uid;
    }
    public long getPid()
    {
        return pid;
    }
    public long getOrder_id()
    {
        return order_id;
    }
}